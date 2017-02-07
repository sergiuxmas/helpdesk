/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.dao.AtribUserRolImpl;
import md.cnam.helpdesk.entity.AtribUserRol;
import md.cnam.helpdesk.entity.ClCategorii;
import md.cnam.helpdesk.entity.ClDiviziuni;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.model.AuthFormModel;
import md.cnam.helpdesk.model.ChangePasswordForm;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.model.UserSession;
import md.cnam.helpdesk.service.AuthenticationService;
import md.cnam.helpdesk.service.ClDiviziuniService;
import md.cnam.helpdesk.service.ClUseriService;
import md.cnam.helpdesk.service.TiketService;
import md.cnam.helpdesk.util.Md5;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UseriController {
    @Autowired AuthenticationService authService;
    @Autowired ClUseriService userService;
    @Autowired AtribUserRolImpl atribUserRolDao;
    @Autowired ClDiviziuniService diviziuniServie;
    @Autowired ObjectMapperM4 mapper;
    @Autowired private HttpSession httpSession;
  
    @RequestMapping(value="auth/index",method = {RequestMethod.GET, RequestMethod.POST})
    public String index(
            @ModelAttribute("user") AuthFormModel user,
            HttpSession httpSession,
            Model model
    ){
        httpSession.invalidate();
        model.addAttribute("user", new AuthFormModel());
        return "authorization";
    }
    
    @RequestMapping(value="auth/reg",method = RequestMethod.POST)
    public String auth(
            @Valid @ModelAttribute("user") AuthFormModel user,
            BindingResult result,
            Model model,
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            @RequestParam(value = "password", required = false, defaultValue = "") String password,
            HttpServletRequest request
    ){
        user.setUsername(username);
        user.setPassword(password);
        if(!result.hasErrors()) {
            try {
                //Properties ticket=authService.getTicket();
                if (authService.authenticate(username,Md5.getMD5(password))) {
                    UserSession sessionUser=(UserSession)request.getSession().getAttribute("user");
                    //System.out.println("categoria:"+sessionUser.getIdCategoria().getId());
                    if (ArrayUtils.contains(sessionUser.getId_categoria(),3)) {//IT
                        return "redirect:/tiket/userDSI/gridlist";
                    } 
                    if (ArrayUtils.contains(sessionUser.getId_categoria(),28)) {//Administrator
                        return "redirect:/tiket/admin/gridlist";
                    }
                    if (ArrayUtils.contains(sessionUser.getId_categoria(),29)) {//Moderator
                        return "redirect:/tiket/admin/gridlist";
                    }
                    return "redirect:/tiket/user/gridlist";
                    //return "redirect:/index";
                }else{
                    model.addAttribute("errorMessage", "Parola nu este corecta sau nu aveti acces");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            user.setPassword("");
            model.addAttribute("user", user);
        }

        //return "forward:/auth/index";
        //return "redirect:/auth/index";
        
        return "authorization";
    }
    
    @RequestMapping(value="user/logout",method = {RequestMethod.GET})
    public String logout(
            HttpSession httpSession
    ){
        httpSession.invalidate();
        return "redirect:/index";
    }
    
    @RequestMapping(value="user/changePassword",method = RequestMethod.GET)
    public String changePassword(
            Model model
    ){
        model.addAttribute("userPassword", new ChangePasswordForm());
        return "user.modify";
    }
    
    @RequestMapping(value="user/changePassword/post",method = RequestMethod.POST)
    public String changePasswordPost(
            @Valid @ModelAttribute("userPassword") ChangePasswordForm changePassword,
            BindingResult result,
            Model model,
            @RequestParam(value = "parolaCurenta", required = false, defaultValue = "") String parolaCurenta,
            @RequestParam(value = "parolaNoua", required = false, defaultValue = "") String parolaNoua,
            @RequestParam(value = "confirmareaParolei", required = false, defaultValue = "") String confirmareaParolei
    ){
        //System.out.println(changePassword.getParolaCurenta());
        if (!parolaCurenta.isEmpty() && !parolaNoua.isEmpty() && parolaNoua.equals(confirmareaParolei)) {
            if(!result.hasErrors()) {
                if (authService.changePassword(parolaCurenta,parolaNoua)) {
                    changePassword.setIsModified(true);
                }
            }
        }
        changePassword.setParolaCurenta("");
        model.addAttribute("userPassword", changePassword);
        model.addAttribute("message", changePassword.getMessage());
        return "user.modify";
    }
    
    @RequestMapping(value="utilizatori",method = RequestMethod.GET)
    public String users(
            Model model
    ){
        
        return "admin.users";
    }
    
    @RequestMapping(value="/userdsi/list",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String list(
            HttpServletRequest request
        ){
        try {
            List nUseri=userService.listDSI();
            Map emptyItem=new HashMap<String,Object>();
            emptyItem.put("id", "");
            emptyItem.put("nume", "...");
            nUseri.add(0, emptyItem);
            
            String json = mapper.writeValueAsString(nUseri);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value="/user/listNeatasati",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String listNeatasati(
            HttpServletRequest request
        ){
        
        JqGridViewModel gridModel=new JqGridViewModel();
        userService.fillNeatasatiGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="/user/edit",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String edit(
            @RequestParam(value = "oper", required = false, defaultValue = "") String oper,
            @RequestParam(value = "id_categoria", required = false, defaultValue = "") String id_categoria,
            @RequestParam(value = "id_diviziune", required = false, defaultValue = "") String id_diviziune,
            @ModelAttribute UserSession user,
            HttpServletRequest request
        ){
        //System.out.println(id_categoria.getClass());
        switch(oper){
            case "edit":{          
                atribUserRolDao.saveOrUpdateOrDel(id_categoria,Integer.parseInt(request.getParameter("id")));
                if (user!=null) {
                    /*
                    @ModelAttribute UserSession user nu initializeaza automat cimpul id_diviziune deoarece in Entity id!=id_diviziune
                    */
                    user.setIdDiviziune(new ClDiviziuni(Integer.parseInt(id_diviziune)));
                    userService.customUpdateWithSession(user);
                }
                break;
            }
            case "del":{
                //ClUseri user=new ClUseri(Integer.parseInt(request.getParameter("id")));
                userService.delete(user);
                break;
            }
        }
        
        JqGridViewModel gridModel=new JqGridViewModel();
        userService.fillGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="/user/gridlist",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String gridlist(
            HttpServletRequest request
        ){
        
        JqGridViewModel gridModel=new JqGridViewModel();
        userService.fillGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="user/modifyProfil",method = {RequestMethod.GET, RequestMethod.POST},produces = {"application/json;charset=UTF-8"})
    public String modifyProfil(
            @RequestParam(value = "diviziune", required = false, defaultValue = "") Integer id_diviziune,
            @RequestParam(value = "noprofile", defaultValue = "") String profile,
            @RequestParam(value = "RedirectUri", defaultValue = "") String redirectUri,
            HttpServletRequest request,
            Model model
    ){
        ClUseri userSession=(ClUseri)httpSession.getAttribute("user");
        
        if (request.getMethod().equals("POST")) {
            UserSession user=new UserSession();
            user.setId(userSession.getId());
            user.setIdDiviziune(new ClDiviziuni(id_diviziune));
            //user.setIdCategoria(new ClCategorii(4));//common
            userService.customUpdate(user);
            
            if (!redirectUri.isEmpty()) {
                return "redirect:"+redirectUri;
            }
        }
        
        List divizTreeList=diviziuniServie.listTree(1,4);

        Integer idDiviziune=(userSession.getIdDiviziune()!=null)?userSession.getIdDiviziune().getIdDiviziune():null;
        
        if (profile.isEmpty()) {
            model.addAttribute("message", "Este necesar de completat profilul Dvs.");
        }else{
            model.addAttribute("message", "");
        }
        model.addAttribute("divizTreeListRendered", diviziuniServie.renderListTreeCombobox(divizTreeList,idDiviziune));
        return "user.modifyProfil";
    }
}
