/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import md.cnam.helpdesk.config.AppConfig;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.dao.ExchFileDao;
import md.cnam.helpdesk.dao.ExchThemesImpl;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.entity.ExchFiles;
import md.cnam.helpdesk.entity.ExchThemes;
import md.cnam.helpdesk.model.ExchFileUpload;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.validators.ExchFileValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import static org.apache.velocity.texen.util.FileUtil.file;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/exchange")
public class FilesexchangeController {
     @Autowired private AppConfig appConfig;
     @Autowired private HttpSession httpSession;
     @Autowired private ExchThemesImpl exchThemeDao;
     @Autowired private ExchFileDao exchFileDao;
     @Autowired private ObjectMapperM4 mapper;
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String load(
       @RequestParam(value = "id", required = false) Integer id,
       Model model
    ){
        ExchThemes tema=new ExchThemes();
        if (id!=null) {
            tema=exchThemeDao.findById(id);
        }
        
        if (tema!=null && tema.getActive()==null) {
            tema.setActive(false);
        }
        model.addAttribute("tema",tema);
        return "exchange.new";
    }
    
    @RequestMapping(value = "/theme", method = RequestMethod.GET)
    public String theme(
    ){
        
        return "exchange.theme";
    }
    
    @RequestMapping(value = "/themeajax", method = RequestMethod.GET)
    public String themeajax(
    ){
        
        return "exchange.themeajax1";
    }
    
    @RequestMapping(value="/themeGridlist",method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public @ResponseBody String gridlistPost(
            HttpServletRequest request
        ){
        ClUseri user=(ClUseri)httpSession.getAttribute("user");
        JqGridViewModel gridModel=new JqGridViewModel();
        gridModel.setSidx("date_created");
        gridModel.autocompleteParameter(request,new String[]{"name","active","date_created"});
        gridModel.getSearchMap().put("id_sessionUser", user.getId());
        gridModel.setRows(exchThemeDao.gridlist(gridModel.getCriterya()));
       
        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value="/addTheme",method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public @ResponseBody Boolean addTheme(
            @RequestParam(value = "newThemeName", required = true) String name
        ){
        if (!name.isEmpty()) {
            ExchThemes newTheme=new ExchThemes();
            newTheme.setName(name);
            ClUseri user=(ClUseri)httpSession.getAttribute("user");
            newTheme.setIdUser(user.getId());
            newTheme.setActive(true);
            newTheme.setDateCreated(new Date());
            try {
                exchThemeDao.save(newTheme);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        
        return false;
    }
    
    @RequestMapping(value="/themeEdit",method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public @ResponseBody Boolean editTheme(
            @RequestParam(value = "oper", required = true) String oper,
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "active", required = false) Boolean active
        ) {
        
        if (oper!=null && oper.equals("edit")) {
            ExchThemes editTheme=exchThemeDao.findById(Integer.parseInt(id));
            editTheme.setName(name);
            editTheme.setActive(active);
            exchThemeDao.update(editTheme);
            return true;
        }
        if (oper!=null && oper.equals("del")) {
                List<Map> files=exchFileDao.getFilesByIdTheme(Integer.parseInt(id));
                for (Map file : files) {
                    try {
                        Path fileToDeletePath = Paths.get(appConfig.getExchFilesLocation()+file.get("url"));
                        java.nio.file.Files.delete(fileToDeletePath);//sterge fisierul fizic
                    } catch (IOException ex) {
                        Logger.getLogger(FilesexchangeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //ExchThemes delTheme=exchThemeDao.findById(id);
                exchThemeDao.delete(Integer.parseInt(id));
                return true;
         }
         if (oper!=null && oper.equals("add")) {
             if (!name.isEmpty()) {
                ExchThemes newTheme=new ExchThemes();
                newTheme.setName(name);
                ClUseri user=(ClUseri)httpSession.getAttribute("user");
                newTheme.setIdUser(user.getId());
                newTheme.setDateCreated(new Date());
                exchThemeDao.save(newTheme);
                return true;
            }
         }
        
        return false;
    }
    
     @RequestMapping(value="/fileEdit",method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public @ResponseBody Boolean editFile(
            @RequestParam(value = "oper", required = true) String oper,
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "filename", required = false) String filename
        ){
        if (oper!=null && oper.equals("edit")) {
            ExchFiles editFile=exchFileDao.findById(id);
            editFile.setFilename(filename);
            exchFileDao.update(editFile);
            return true;
        }else if (oper!=null && oper.equals("del")) {
            ExchFiles delFile=exchFileDao.findById(id);
            Path fileToDeletePath = Paths.get(appConfig.getExchFilesLocation()+delFile.getUrl());
            try {
                java.nio.file.Files.delete(fileToDeletePath);//sterge fisierul fizic
            } catch (IOException ex) {
                Logger.getLogger(FilesexchangeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            exchFileDao.delete(delFile);
            return true;
        }
        
        return false;
    }
    
    @RequestMapping(value="/upload",method = RequestMethod.GET)
    public String uploadGet(
            @ModelAttribute("formModel") ExchFileUpload form,
            Model model
    ){

        model.addAttribute("formModel", form);
        return "exchange.uploadFileajax";
    }
    
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public String uploadPost(
            @ModelAttribute("formModel") ExchFileUpload form,
            //@RequestParam MultipartFile file,
            BindingResult result,
            Model model
    )
    {
        ExchFileValidator fileValidator=new ExchFileValidator();
        fileValidator.validate(form, result);
       
        if(result.hasErrors()) {
            model.addAttribute("formModel", form);
            return "exchange.uploadFileajax";
        }
        
        ClUseri user=(ClUseri)httpSession.getAttribute("user");
        MultipartFile fileUploaded = form.getFileupload();
        if (fileUploaded.getOriginalFilename()!=null && !fileUploaded.getOriginalFilename().isEmpty()) {
            try {
                //Modificarea denumirii
                SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
                SimpleDateFormat dfyear=new SimpleDateFormat("yyyy");

                String newFilename=df.format(new Date())+"."+FilenameUtils.getExtension(fileUploaded.getOriginalFilename());
                //copierea fisierului pe server cu denumirea modificata
                InputStream in = fileUploaded.getInputStream();
                File destination = new File(appConfig.getExchFilesLocation()+ dfyear.format(new Date())+ File.separator + newFilename);
                FileUtils.copyInputStreamToFile(in, destination);

                //inregistrarea in baza de date
                ExchFiles fileEnt=new ExchFiles();
                fileEnt.setIdTema(form.getIdTema());
                fileEnt.setIdUser(user.getId());
                fileEnt.setUrl(dfyear.format(new Date())+ File.separator +newFilename);
                fileEnt.setFilename(FilenameUtils.removeExtension(fileUploaded.getOriginalFilename()));
                fileEnt.setExtention(FilenameUtils.getExtension(fileUploaded.getOriginalFilename()));
                fileEnt.setDateUpload(new Date());
                exchFileDao.save(fileEnt);
            } catch (IOException ex) {
                Logger.getLogger(TiketUserController.class.getName()).log(Level.SEVERE, "Nu s-a incarcat fisierul atasat la tiket", ex);
            }
        }
        
        
        model.addAttribute("formModel", form);
        return "exchange.uploadFileajax";
    }
    
    @RequestMapping(value="/gridlist",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    @ResponseBody
    public String gridlist(
            @RequestParam(value = "idTema", required = true) Integer idTema,
            HttpServletRequest request,
            Model model
    ){

        ClUseri user=(ClUseri)httpSession.getAttribute("user");
        JqGridViewModel gridModel=new JqGridViewModel();
        gridModel.setSidx("date_upload");
        gridModel.autocompleteParameter(request,new String[]{"id_user","filename","date_upload","filename"});
        gridModel.getSearchMap().put("id_sessionUser", user.getId());
        gridModel.getSearchMap().put("id_tema", idTema);
        gridModel.setRows(exchFileDao.gridlist(gridModel.getCriterya()));
        
        //tiketService.fillUserGridModel(request,gridModel);

        try {
            return mapper.writeValueAsString(gridModel);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value = "/sharedDocs", method = RequestMethod.GET)
    public String sharedDocs(
    ){
        
        return "exchange.sharedDocs";
    }
    
    @RequestMapping(value = "/sharedDocsGridlist", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public @ResponseBody String sharedDocsGridlist(
            HttpServletRequest request
    ){
        ClUseri user=(ClUseri)httpSession.getAttribute("user");
        JqGridViewModel gridModel=new JqGridViewModel();
        gridModel.setSidx("date_created");
        gridModel.autocompleteParameter(request,new String[]{"name","nume","date_created"});
        gridModel.setRows(exchThemeDao.sharedDocs(gridModel.getCriterya()));
       
        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value = "/sharedDocView", method = RequestMethod.GET)
    public String sharedDocView(
       @RequestParam(value = "id", required = false) Integer id,
       Model model
    ){
        ExchThemes tema=new ExchThemes();
        if (id!=null) {
            tema=exchThemeDao.findById(id);
        }
 
        model.addAttribute("tema",tema);
        return "exchange.sharedDocView";
    }
    
    @RequestMapping(value="/sharedGridlist",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    @ResponseBody
    public String sharedGridlist(
            @RequestParam(value = "idTema", required = true) Integer idTema,
            HttpServletRequest request,
            Model model
    ){

        JqGridViewModel gridModel=new JqGridViewModel();
        gridModel.setSidx("date_upload");
        gridModel.autocompleteParameter(request,new String[]{"id_user","filename","date_upload","filename"});
        gridModel.getSearchMap().put("id_tema", idTema);
        gridModel.setRows(exchFileDao.sharedGridlist(gridModel.getCriterya()));
        
        //tiketService.fillUserGridModel(request,gridModel);

        try {
            return mapper.writeValueAsString(gridModel);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(UseriController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public void getFile(
            @RequestParam(value = "id", required = true) Integer id,
            HttpServletResponse response
    ){
      
        FileInputStream is=null;
        ExchFiles file=exchFileDao.findById(id);
        try {
            //String value = new String(file.getFilename().getBytes("UTF-8"));
            //System.out.println(value);
            //is = new InputStreamReader(new FileInputStream(appConfig.getExchFilesLocation()+file.getUrl()), "UTF8");
            is = new FileInputStream(appConfig.getExchFilesLocation()+file.getUrl());
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(file.getFilename()+"."+file.getExtention(),"UTF-8"));//+file.getFilename()
            response.setCharacterEncoding("utf-8");
            //IOUtils.copy(is, response.getOutputStream(), "UTF-8");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(FilesexchangeController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @RequestMapping(value = "/updateStateTheme", method = RequestMethod.POST, headers="Accept=application/json")
    @ResponseBody
    public void updateStateTheme(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "active", required = true) Boolean active
    ){
        ExchThemes tema=exchThemeDao.findById(id);
        tema.setActive(active);
        exchThemeDao.update(tema);
    }
}
