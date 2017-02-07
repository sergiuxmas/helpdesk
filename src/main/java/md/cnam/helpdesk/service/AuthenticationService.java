/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBElement;
import md.cnam.helpdesk.config.AppConfig;
import md.cnam.helpdesk.entity.ClCategorii;
import md.cnam.helpdesk.entity.ClDiviziuni;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.model.UserSession;
import md.cnam.helpdesk.util.Md5;
import md.cnam.helpdesk.wsdl.ExcahgePassword;
import md.cnam.helpdesk.wsdl.ExcahgePasswordResponse;
import md.cnam.helpdesk.wsdl.GetAutentificationInfo;
import md.cnam.helpdesk.wsdl.GetAutentificationInfoResponse;
import md.cnam.helpdesk.wsdl.ObjectFactory;
import md.cnam.helpdesk.wsdl.WcfLogin;
import md.cnam.helpdesk.wsdl.WcfLoginData;
import md.cnam.helpdesk.wsdl.WcfLoginResponse;
import md.cnam.helpdesk.wsdl.WebAuthorization;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.StringUtils;

/**
 *
 * @author Admin
 */
@Service
public class AuthenticationService {
    @Autowired
    private WebServiceTemplate webServiceTemplate;
    
    @Autowired
    private ClUseriService userService;
    
    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    private AppConfig appConfig;
    
    private PrintWriter outLog=null;//test
    
    //@Autowired
    //private ApplicationContext appContext;
    
    public Properties getTicket(){
        if (outLog==null) {
            try {
                outLog = new PrintWriter(appConfig.getLogUrl());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //String configFile="C:/Users/Admin/Documents/NetBeansProjects/helpdesk3/src/main/resources/authentication-service.conf";
        //String ticketFile="C:/Users/Admin/Documents/NetBeansProjects/helpdesk3/src/main/resources/local-ticket.data";
        //System.out.println("TicketDataUrl="+appConfig.getTicketDataUrl());
        Resource configResource = customerService.getResource("classpath:authentication-service.conf");
        File ticketDataUrl = new File(appConfig.getTicketDataUrl());// "classpath:local-ticket.data"
        //outLog.println("ticketDataUrl:"+ticketDataUrl.getAbsolutePath());
        
        Properties pTicket = null;
        Date currentDate = GregorianCalendar.getInstance().getTime();
        //pTicket.setProperty("date", new DateTime(currentDate).toString("dd.MM.yyyy"));
        
        try {
            //outLog.println("getTicket() try");
            //verificam ticket-ul local
            pTicket = new Properties();
            //pTicket.load(new FileInputStream(ticketResource.getFile()));//ticketFile
            pTicket.load(new FileInputStream(ticketDataUrl));//ticketFile
            //outLog.println("\n getTicket() getProperty(date):"+pTicket.getProperty("date")+"\t getProperty(ticket):"+pTicket.getProperty("ticket"));
       
            if (pTicket.containsKey("date") &&
                !pTicket.getProperty("date").equals(new DateTime(currentDate).toString("dd.MM.yyyy"))) {
                //facem interogarea la web serviciu
                //initiem configurarea
                Properties pWcf = new Properties();
                pWcf.load(new FileInputStream(configResource.getFile()));//configFile
                String wcfUser=pWcf.getProperty("wcfUser");
                String wcfPassword=pWcf.getProperty("wcfPassword");
                
                //outLog.println("\n pWcf.getProperty(\"wcfUser\":)"+pWcf.getProperty("wcfUser")+"\t pWcf.getProperty(\"wcfPassword\":)"+pWcf.getProperty("wcfPassword"));
                //outLog.println("getTicket> configResource="+configResource.getFile().getAbsolutePath());
                outLog.println("getTicket> wcfUser="+wcfUser+"\t wcfPassword="+wcfPassword);

                //cream obiectul WcfLogin pentru web serviciu
                ObjectFactory factory=new ObjectFactory();
                JAXBElement login=factory.createWcfLoginWcfUser(wcfUser);
                JAXBElement password=factory.createWcfLoginWcfPassword(wcfPassword);
                WcfLogin wcfLogin=factory.createWcfLogin();
                wcfLogin.setWcfUser(login);
                wcfLogin.setWcfPassword(password);
                      
                //extracem ticketul
                try {
                    //https://ankeetmaini.wordpress.com/2011/12/01/spring-web-services-client/
                    WcfLoginResponse loginResponse=(WcfLoginResponse)webServiceTemplate.marshalSendAndReceive(wcfLogin, new SoapActionCallback("http://tempuri.org/ILoginInterface/wcfLogin"));
                    WcfLoginData loginData=loginResponse.getWcfLoginResult().getValue();

                    //outLog.println("\n loginResponse:"+loginResponse.toString());
                    outLog.println("getTicket> loginData.getResultCode()="+loginData.getResultCode());
                    
                    switch(loginData.getResultCode()){
                    case 0:{
                        //Login successful!
                        pTicket.setProperty("date", new DateTime(currentDate).toString("dd.MM.yyyy"));
                        pTicket.setProperty("ticket", loginData.getTicket().getValue());
                        //inregistrarea in fisier
                        OutputStream output = new FileOutputStream(ticketDataUrl);//ticketFile
                        pTicket.store(output, null);
                        break;
                    }
                    case 1:{
                        //Access denied!
                        Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:1. Access denied!");
                        break;
                    }
                    default:{
                        //Service internal error!
                        Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:default. Internal service error!");
                        break;
                        }
                    }
                    /*
                    Logger.getLogger(AuthenticationService.class.getName()).log(Level.INFO,
                        "Message: "+loginData.getMessage().getValue()+
                        "\n Cod:"+loginData.getResultCode()+
                        "\n token: "+loginData.getTicket().getValue());
                    */
                    } catch (Exception e) {
                        Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null,e);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pTicket;
    }
    
    public boolean authenticate(String username, String password) throws FileNotFoundException{
        //Logger.getLogger(AuthenticationService.class.getName()).warning("TEST!");
        
        //http://stackoverflow.com/questions/3093423/getting-spring-mvc-relative-path
        //String configFile="C:/Users/Admin/Documents/NetBeansProjects/helpdesk3/src/main/resources/authentication-service.conf";
        
        if (!appConfig.isAuthorizationRemote()) {
            Map userDB=userService.findByLoginAndPassword(username, password);
            if (userDB==null) {
                return false;
            }
            UserSession userSession = new UserSession();
            userSession.setId(Integer.parseInt(String.valueOf(userDB.get("id"))));
            userSession.setIdloginCnam(Integer.parseInt(String.valueOf(userDB.get("id_loginCnam"))));
            userSession.setUsername((String)userDB.get("username"));
            userSession.setIdnp(String.valueOf(userDB.get("idnp")));
            userSession.setNume((String)userDB.get("nume"));
            userSession.setPrenume((String)userDB.get("prenume"));
            userSession.setAtasat((String)userDB.get("atasat"));
            //if (userDB.get("id_diviziune") != null) {
                userSession.setIdDiviziune(new ClDiviziuni(Integer.parseInt(String.valueOf(userDB.get("id_diviziune")))));
            //}
            userSession.setDiviziuneNume((String)userDB.get("diviziune_nume"));
            /*
            Transforma lista rolurilor din string in int[] (pentru id) si String[] (pentru nume)
            */
            //if (userDB.get("id_categoria")!=null) {
            userSession.setId_categoria(Ints.toArray(Lists.newArrayList(Ints.stringConverter().convertAll(Splitter.on(",").split((String)userDB.get("id_categoria"))))));//Google Guava
            //}
            userSession.setCategoria_nume(StringUtils.delimitedListToStringArray((String)userDB.get("id_categoria_nume"), ","));
                            
            //salvarea in sesiune
            httpSession.setAttribute("user", userSession);
            return true;
        }
        
        Resource configResource = customerService.getResource("classpath:authentication-service.conf");
        
        ObjectFactory factory=new ObjectFactory();
        Properties config = new Properties();
        
        try {
            if (outLog==null) {
            try {
                outLog = new PrintWriter(appConfig.getLogUrl());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
            config.load(new FileInputStream(configResource.getFile()));//configFile
            GetAutentificationInfo authInfo=factory.createGetAutentificationInfo();
            //authInfo.setIsAuthorizated(factory.createGetAutentificationInfoIsAuthorizated(username+Md5.getMD5(password)+config.getProperty("idSystem")));
            authInfo.setIsAuthorizated(factory.createGetAutentificationInfoIsAuthorizated(username+password+config.getProperty("idSystem")));
            authInfo.setWcfTicket(factory.createGetAutentificationInfoWcfTicket(getTicket().getProperty("ticket")));
            GetAutentificationInfoResponse authResponse=(GetAutentificationInfoResponse)webServiceTemplate.marshalSendAndReceive(authInfo, new SoapActionCallback("http://tempuri.org/IAutentificationService/GetAutentificationInfo"));
            WebAuthorization webAuth=authResponse.getGetAutentificationInfoResult().getValue();
            /*
            System.out.println(
                    "\nusername:"+username+
                    //"\npassword:"+password+
                    "\nidSystem:"+config.getProperty("idSystem")+
                    "\nTicket:"+getTicket().getProperty("ticket")+
                    "\nAlow:"+webAuth.getAlow()+
                    "\nAttached:"+webAuth.getAttached().getValue()+
                    "\nFullName:"+webAuth.getFullName().getValue()+
                    "\nResultCode:"+webAuth.getResultCode()+
                    "\nUserId:"+webAuth.getUserId()
            );
            */
            
            switch(webAuth.getResultCode()){
                case 0:{
                    switch(webAuth.getAlow()){
                        case 1:{
                            //Autorizare reusita
                            //Datele din web-service
                            ClUseri userLogat=new ClUseri();
                            userLogat.setIdloginCnam(webAuth.getUserId());
                            userLogat.setUsername(username);
                            userLogat.setNume(webAuth.getFullName().getValue());
                            userLogat.setAtasat(webAuth.getAttached().getValue());
                            
                            Map userDB=userService.findByIdLogin(webAuth.getUserId());
                            if (userDB==null) {
                                userService.save(userLogat);//pentru userul inregistrat nu-i definit rolul
                            }else{
                               userLogat.setId((Integer)userDB.get("id"));
                               userService.customUpdate(userLogat);
                               userDB=userService.findByIdLogin(webAuth.getUserId());//se actualizeaza dupa update
                            }
                            
                            UserSession userSession = new UserSession();
                            userSession.setId(Integer.parseInt(String.valueOf(userDB.get("id"))));
                            userSession.setIdloginCnam(Integer.parseInt(String.valueOf(userDB.get("id_loginCnam"))));
                            userSession.setUsername((String)userDB.get("username"));
                            userSession.setIdnp(String.valueOf(userDB.get("idnp")));
                            userSession.setNume((String)userDB.get("nume"));
                            userSession.setPrenume((String)userDB.get("prenume"));
                            userSession.setAtasat((String)userDB.get("atasat"));
                            if (userDB.get("id_diviziune") != null) {
                                userSession.setIdDiviziune(new ClDiviziuni(Integer.parseInt(String.valueOf(userDB.get("id_diviziune")))));
                            }
                            userSession.setDiviziuneNume((String)userDB.get("diviziune_nume"));
                            /*
                            Transforma lista rolurilor din string in int[] (pentru id) si String[] (pentru nume)
                            */
                            if (userDB.get("id_categoria")!=null) {
                                userSession.setId_categoria(Ints.toArray(Lists.newArrayList(Ints.stringConverter().convertAll(Splitter.on(",").split((String)userDB.get("id_categoria"))))));//Google Guava
                            }
                            userSession.setCategoria_nume(StringUtils.delimitedListToStringArray((String)userDB.get("id_categoria_nume"), ","));
                            
                            //salvarea in sesiune
                            httpSession.setAttribute("user", userSession);
                            /*
                            System.out.println(
                                "ID="+userSession.getId()+"\n"+
                                "IdloginCnam="+userSession.getIdloginCnam()+"\n"+
                                "Nume="+userSession.getNume()+"\n"+
                                "IdDiviziune="+userSession.getIdDiviziune()+"\n"+
                                "DiviziuneNume="+userSession.getDiviziuneNume()+"\n"+
                                "Id_categoria="+Arrays.toString(userSession.getId_categoria())+"\n"+
                                "Categoria_nume="+Arrays.toString(userSession.getCategoria_nume())+"\n"+
                                "Atasat="+userSession.getAtasat()
                            );
                            */
                            outLog.println("authenticate> webAuth.getAlow() case:1. Autorizare reusita");
                            outLog.println("case 1: webAuth.getUserId()="+webAuth.getUserId());
                            return true;
                            //break;
                        }
                        case 0:{
                            //Autorizare nereusita
                            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:0. Autorizare nereusita");
                            outLog.println("authenticate> webAuth.getAlow() case:0. Autorizare nereusita");
                            outLog.println("case 0: webAuth.getUserId()="+webAuth.getUserId());
                            break;
                        }
                        default:{
                            //Service internal error!
                            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:default. Internal service error!");
                            break;
                        }
                    }
                    break;
                }
                case 1:{
                    //Interogare nereusita
                    Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:1. Interogare nereusita");
                    outLog.println("authenticate> webAuth.getResultCode() case:1. Autorizare nereusita");
                    break;
                }
                default:{
                    //Service internal error!
                    Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:default. Internal service error!");
                    outLog.println("authenticate> webAuth.getResultCode() case:default. Internal service error! \n webAuth.getResultCode():"+webAuth.getResultCode()+
                            "\n getTicket().getProperty(\"ticket\"):"+getTicket().getProperty("ticket"));
                    break;
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            outLog.close();
        }
        
        return false;
    }
    
    public boolean changePassword(String parolaCurenta,String parolaNoua){
        ObjectFactory factory=new ObjectFactory();
        
        GetAutentificationInfo authInfo=factory.createGetAutentificationInfo();
        ClUseri sessionUser=(ClUseri)httpSession.getAttribute("user");
        authInfo.setIsAuthorizated(factory.createGetAutentificationInfoIsAuthorizated(sessionUser.getIdloginCnam()+Md5.getMD5(parolaCurenta)));
        authInfo.setWcfTicket(factory.createGetAutentificationInfoWcfTicket(getTicket().getProperty("ticket")));
        
        ExcahgePassword exPass=factory.createExcahgePassword();
        exPass.setIsAuthorizated(authInfo.getIsAuthorizated());
        exPass.setNewPassword(factory.createExcahgePasswordNewPassword(Md5.getMD5(parolaNoua)));
        exPass.setWcfTicket(factory.createExcahgePasswordWcfTicket(getTicket().getProperty("ticket")));
        
        ExcahgePasswordResponse passResponse=(ExcahgePasswordResponse)webServiceTemplate.marshalSendAndReceive(exPass, new SoapActionCallback("http://tempuri.org/IAutentificationService/ExcahgePassword"));
        WebAuthorization webAuth=passResponse.getExcahgePasswordResult().getValue();
        
        /*
        System.out.println(
                    "\nIdUser:"+sessionUser.getIdloginCnam()+
                    "\nparolaCurenta:"+parolaCurenta+
                    "\nparolaNoua:"+parolaNoua+
                    "\nTicket:"+getTicket().getProperty("ticket")+
                    "\nAlow:"+webAuth.getAlow()+
                    "\nAttached:"+webAuth.getAttached().getValue()+
                    "\nFullName:"+webAuth.getFullName().getValue()+
                    "\nResultCode:"+webAuth.getResultCode()+
                    "\nUserId:"+webAuth.getUserId()
            );
        */
                
        switch(webAuth.getResultCode()){
            case 0:{
                //Interogare reusita
                switch(webAuth.getAlow()){
                    case 0:{
                        //Autorizare nereusita
                        
                        break;
                    }
                    case 1:{
                        //Autorizare reusita
                        return true;
                        //break;
                    }
                    default:{
                            //Service internal error!
                            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:default. Internal service error!");
                            break;
                    }
                }
                break;
            }
            case 1:{
                //Interogare nereusita
                Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:1. Interogare nereusita");
                break;
            }
            default:{
            //Service internal error!
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, "case:default. Internal service error!");
            break;
           }
        }
        
        return false;
    }
}
