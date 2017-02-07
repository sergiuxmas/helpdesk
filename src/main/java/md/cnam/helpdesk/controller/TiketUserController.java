/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import md.cnam.helpdesk.config.AppConfig;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.entity.Files;
import md.cnam.helpdesk.entity.Messages;
import md.cnam.helpdesk.entity.Solicitare;
import md.cnam.helpdesk.model.ExecutareFormModel;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.model.TiketForm;
import md.cnam.helpdesk.service.ClDomeniiService;
import md.cnam.helpdesk.service.ClPrioritateService;
import md.cnam.helpdesk.service.ClSolicitareTipService;
import md.cnam.helpdesk.service.ClUseriService;
import md.cnam.helpdesk.service.ExecutareService;
import md.cnam.helpdesk.service.ExecutorHistoryService;
import md.cnam.helpdesk.service.FilesService;
import md.cnam.helpdesk.service.MessagesService;
import md.cnam.helpdesk.service.TiketOpenHistoryService;
import md.cnam.helpdesk.service.TiketService;
import md.cnam.helpdesk.validators.FileValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/tiket")
public class TiketUserController {
    @Autowired
    TiketService tiketService;
    
    @Autowired
    TiketOpenHistoryService tiketOpenService;
    
    @Autowired
    ClDomeniiService domeniiService;
    
    @Autowired
    ClPrioritateService prioritateService;
    
    @Autowired
    ClUseriService userService;
    
    @Autowired
    ClSolicitareTipService solTipService;
    
    @Autowired
    ExecutorHistoryService ehService;
    
    @Autowired
    ExecutareService executareService;
    
    @Autowired
    MessagesService messageService;
    
    @Autowired
    FilesService filesService;
    
    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    @Autowired
    private AppConfig appConfig;
    
    //@Autowired
    //private FileValidator fileValidator;

    //@InitBinder
    //protected void initBinderFileModel(WebDataBinder binder) {
        //binder.setValidator(fileValidator);
    //}
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String creareaSolicitarii(
            HttpServletRequest request,
            Model model
    ){
        ClUseri user=(ClUseri)httpSession.getAttribute("user");
        if (user.getIdDiviziune()==null) {
            return "redirect:/user/modifyProfil?noprofile&RedirectUri=/tiket/new";
        }
        
        model.addAttribute("listaTipurilor", solTipService.list());
        model.addAttribute("listaDomeniilor", domeniiService.list());
        model.addAttribute("listaPrioritatilor", prioritateService.list());
        model.addAttribute("formModel", new TiketForm());
        
        return "tiket.new";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String creareaSolicitariiPost(
            /*@Valid*/ @ModelAttribute("formModel") TiketForm form,
            BindingResult result,
            Model model
    ){
        model.addAttribute("listaTipurilor", solTipService.list());
        model.addAttribute("listaDomeniilor", domeniiService.list());
        model.addAttribute("listaPrioritatilor", prioritateService.list());
        
        /*
        //http://stackoverflow.com/questions/13083036/how-do-i-display-validation-errors-about-an-uploaded-multipart-file-using-spring
        String mimeType = determineMimeType(myModelAttribute.getFile().getBytes());
        if (mimeType.equalsIgnoreCase("application/pdf")){
            result.addError(new ObjectError("file", "pdf not accepted"));
        }
        */
        
        FileValidator fileValidator=new FileValidator();//http://memorynotfound.com/spring-mvc-file-upload-example-validator/
        fileValidator.validate(form, result);
        if(result.hasErrors()) {
            model.addAttribute("formModel", form);
            return "tiket.new";
           //return "forward:/tiket/new";
        }
        
        ClUseri user=(ClUseri)httpSession.getAttribute("user");    
        
        Solicitare solicitare=new Solicitare();
        solicitare.setTip(Integer.parseInt(form.getTip()));
        solicitare.setIdDomeniu(Integer.parseInt(form.getDomeniu()));
        solicitare.setIdPrioritate(Integer.parseInt(form.getPrioritate()));
        solicitare.setDescriere(form.getDescrierea());
        solicitare.setIdUser(user.getId());
        solicitare.setDataSolicitarii(new Date());
        solicitare.setStatut(1);//noua
        //ExecutorHistory execHistory=tiketService.defineDSIResponsible(solicitare);
        tiketService.save(solicitare);
        
        MultipartFile fileUploaded = form.getFileUpload();
        if (fileUploaded.getOriginalFilename()!=null && !fileUploaded.getOriginalFilename().isEmpty()) {
            try {
                //Modificarea denumirii
                SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
                SimpleDateFormat dfyear=new SimpleDateFormat("yyyy");

                String newFilename=df.format(new Date())+"_"+solicitare.getId()+"."+FilenameUtils.getExtension(fileUploaded.getOriginalFilename());
                //copierea fisierului pe server cu denumirea modificata
                InputStream in = fileUploaded.getInputStream();
                File destination = new File(appConfig.getFilesLocation()+ dfyear.format(new Date())+ File.separator + newFilename);
                FileUtils.copyInputStreamToFile(in, destination);

                //inregistrarea in baza de date
                Files fileEnt=new Files();
                fileEnt.setIdTiket(solicitare.getId());
                fileEnt.setIdUser(user.getId());
                fileEnt.setUrl(dfyear.format(new Date())+ File.separator +newFilename);
                fileEnt.setFilename(fileUploaded.getOriginalFilename());
                fileEnt.setDateUpload(new Date());
                filesService.save(fileEnt);
            } catch (IOException ex) {
                Logger.getLogger(TiketUserController.class.getName()).log(Level.SEVERE, "Nu s-a incarcat fisierul atasat la tiket", ex);
            }
        }
        
        return "redirect:/tiket/user/gridlist";
    }
    
    @RequestMapping(value = "/user/gridlist", method = RequestMethod.GET)
    public String gridlistGet(){
        return "user.tiket.list";
    }
    
    @RequestMapping(value="/user/gridlist",method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public @ResponseBody String gridlistPost(
            HttpServletRequest request
        ){
        ClUseri user=(ClUseri)httpSession.getAttribute("user");
        JqGridViewModel gridModel=new JqGridViewModel();
        gridModel.getSearchMap().put("id_sessionUser", user.getId());
        tiketService.fillUserGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value = "/user/view", method = {RequestMethod.GET})
    public String viewUserDSI(
            @RequestParam(value = "id", required = true) Integer id,
            Model model
    ){
        if (id!=null) {
            Solicitare sol=new Solicitare(id);
            sol.setOpened(true);
            tiketService.updateOpenedTiket(sol);
            tiketOpenService.saveOrUpdate(id);
        }
        Map tiket=tiketService.getTiket(id);
        Map executareInfo=executareService.findByIdTiket(id);
        
        TiketForm solicitareModel=new TiketForm();
        solicitareModel.setDescrierea((String)tiket.get("descriere"));
        model.addAttribute("userList", userService.listDSI());
        model.addAttribute("idSolicitare", id);
        model.addAttribute("tiket", tiket);
        model.addAttribute("prioritateList", prioritateService.list());
        model.addAttribute("tipList", solTipService.list());
        model.addAttribute("domeniuList", domeniiService.list());
        model.addAttribute("executareInfo", executareInfo);
        model.addAttribute("fileList", filesService.getFilesByIdTiket(id));
        model.addAttribute("solicitareModel", solicitareModel);
        return "user.tiket.view";
    }
    
    @ResponseBody
    @RequestMapping(value = "/user/saveMessage", method = RequestMethod.POST)
    public void saveMessage(
            @RequestParam(value = "idTiket") Integer idTiket,
            @RequestParam(value = "newmessage") String newmessage
    ){
        ClUseri sessionUser=(ClUseri)httpSession.getAttribute("user");
        Messages newMessage=new Messages();
        newMessage.setIdSolicitare(idTiket);
        newMessage.setMessage(newmessage);
        newMessage.setIdExecutor(sessionUser.getId());
        newMessage.setDataExpedierii(new Date());
        messageService.save(newMessage);
        tiketOpenService.saveOrUpdate(idTiket);
    }
    
    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String editTiketGet(
            @ModelAttribute("formModel") TiketForm form,
            @RequestParam(value = "idTiket") Integer idTiket,
            Model model
    ){
        Map tiket=tiketService.getTiket(idTiket);
        form.setId(idTiket);
        form.setTip(String.valueOf(tiket.get("id_tip")));
        form.setPrioritate(String.valueOf(tiket.get("id_prioritate")));
        form.setDomeniu(String.valueOf(tiket.get("id_domeniu")));
        form.setDescrierea(String.valueOf(tiket.get("descriere")));
        
        model.addAttribute("listaTipurilor", solTipService.list());
        model.addAttribute("listaDomeniilor", domeniiService.list());
        model.addAttribute("listaPrioritatilor", prioritateService.list());
        model.addAttribute("formModel", form);
        model.addAttribute("fileList", filesService.getFilesByIdTiket(idTiket));
        
        return "tiket.edit";
    }
    
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editTiketPost(
            @RequestParam(value = "command", defaultValue = "update") String command,
            @RequestParam(value = "fileDel", defaultValue = "") String fileDel,
            @ModelAttribute("formModel") TiketForm form,
            BindingResult result,
            Model model,
            HttpServletResponse response
    ) {
        model.addAttribute("listaTipurilor", solTipService.list());
        model.addAttribute("listaDomeniilor", domeniiService.list());
        model.addAttribute("listaPrioritatilor", prioritateService.list());
        model.addAttribute("formModel", form);
        
        if (command.equals("deleteFile") && !fileDel.isEmpty()) {
            System.out.println("se sterge fisierul");
            try {
                Files file=filesService.findById(Integer.valueOf(fileDel));
                //System.out.println(appConfig.getFilesLocation()+file.getUrl());
                Path fileToDeletePath = Paths.get(appConfig.getFilesLocation()+file.getUrl());
                java.nio.file.Files.delete(fileToDeletePath);//sterge fisierul fizic
                
                filesService.delete(new Files(Integer.parseInt(fileDel)));//sterge din BD
            } catch (Exception e) {
                Logger.getLogger(TiketUserController.class.getName()).log(Level.SEVERE, "Nu s-a sters fisierul", e);
            }
            
            model.addAttribute("fileList", filesService.getFilesByIdTiket(form.getId()));
            return "tiket.edit";
        }
        model.addAttribute("fileList", filesService.getFilesByIdTiket(form.getId()));
        
        FileValidator fileValidator=new FileValidator();
        fileValidator.validate(form, result);
        if(result.hasErrors()) {
            model.addAttribute("formModel", form);
            return "tiket.edit";
        }
        
        ClUseri user=(ClUseri)httpSession.getAttribute("user");    
        
        Solicitare solicitare=new Solicitare();
        solicitare.setId(form.getId());
        solicitare.setTip(Integer.parseInt(form.getTip()));
        solicitare.setIdDomeniu(Integer.parseInt(form.getDomeniu()));
        solicitare.setIdPrioritate(Integer.parseInt(form.getPrioritate()));
        solicitare.setDescriere(form.getDescrierea());
        solicitare.setIdUser(user.getId());
        solicitare.setDataSolicitarii(new Date());
        solicitare.setStatut(1);//noua
        //ExecutorHistory execHistory=tiketService.defineDSIResponsible(solicitare);
        tiketService.update(solicitare);
        
        MultipartFile fileUploaded = form.getFileUpload();
        if (fileUploaded.getOriginalFilename()!=null && !fileUploaded.getOriginalFilename().isEmpty()) {
            try {
                //Modificarea denumirii
                SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
                SimpleDateFormat dfyear=new SimpleDateFormat("yyyy");

                String newFilename=df.format(new Date())+"_"+solicitare.getId()+"."+FilenameUtils.getExtension(fileUploaded.getOriginalFilename());
                //copierea fisierului pe server cu denumirea modificata
                InputStream in = fileUploaded.getInputStream();
                File destination = new File(appConfig.getFilesLocation()+ dfyear.format(new Date())+ File.separator + newFilename);
                FileUtils.copyInputStreamToFile(in, destination);

                //inregistrarea in baza de date
                Files fileEnt=new Files();
                fileEnt.setIdTiket(solicitare.getId());
                fileEnt.setIdUser(user.getId());
                fileEnt.setUrl(dfyear.format(new Date())+ File.separator +newFilename);
                fileEnt.setFilename(fileUploaded.getOriginalFilename());
                fileEnt.setDateUpload(new Date());
                filesService.save(fileEnt);
            } catch (IOException ex) {
                Logger.getLogger(TiketUserController.class.getName()).log(Level.SEVERE, "Nu s-a incarcat fisierul atasat la tiket", ex);
            }
        }
        
        //try {
            //response.sendRedirect("/helpdesk/tiket/user/view?id=" + form.getId());
            return "redirect:/tiket/user/view?id="+ form.getId();
            //return "forward:/tiket/user/view";
            //return "user.tiket.view";
        //} catch (IOException ex) {
            //Logger.getLogger(TiketUserController.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        //return "redirect:/tiket/user/gridlist"; //nu tre sa ajunga aici
    }
    
    @RequestMapping(value = "/user/messagesList", method = RequestMethod.GET)
    public String messagesList(
            @RequestParam(value = "idTiket") Integer idTiket,
            Model model
    ){
        if (httpSession.getAttribute("user")==null) {
            model.addAttribute("messagesList", "refresh");
        }
        model.addAttribute("messagesList", messageService.listSortAsc(idTiket));
        return "userDSI.tiket.messagesList";
    }
    
    @RequestMapping(value = "/user/responsableHistory", method = RequestMethod.GET)
    @ResponseBody
    public List responsableHistory(
            @RequestParam(value = "idTiket", required = true) Integer idTiket
    ){

        return tiketService.responsableTikets(idTiket);
    }
}
