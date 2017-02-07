package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.entity.ClCategorii;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.service.ClCategoriiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoriiController {
    
    @Autowired
    private ClCategoriiService categoriiService;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    @RequestMapping(value="/categorii",method = RequestMethod.GET)
    public String index(){
        return "admin.categorii";
    }
    
    @RequestMapping(value="/categorii/gridlist",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String list(
            HttpServletRequest request
        ){
        JqGridViewModel gridModel=new JqGridViewModel();
        categoriiService.fillGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="/categorii/list",method = {RequestMethod.GET}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String listAjax(
            HttpServletRequest request
        ){

        try {
            String json = mapper.writeValueAsString(categoriiService.list());
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="/categorii/edit",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String edit(
            HttpServletRequest request
        ){
        switch(request.getParameter("oper")){
            case "add":{
                ClCategorii categorieNew=new ClCategorii();
                categorieNew.setNume(request.getParameter("nume"));
                categoriiService.save(categorieNew);
                break;
            }
            case "edit":{
                ClCategorii categorie=new ClCategorii(Integer.parseInt(request.getParameter("id")), request.getParameter("nume"));
                categoriiService.update(categorie);
                break;
            }
            case "del":{
                ClCategorii categorie=new ClCategorii(Integer.parseInt(request.getParameter("id")));
                categoriiService.delete(categorie);
                break;
            }
        }
        
        JqGridViewModel gridModel=new JqGridViewModel();
        categoriiService.fillGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
}
    
    