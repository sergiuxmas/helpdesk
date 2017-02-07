/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.entity.AtribMeniuRol;
import md.cnam.helpdesk.entity.Meniu;
import md.cnam.helpdesk.model.JqxTreeGridModel;
import md.cnam.helpdesk.model.MenuGuest;
import md.cnam.helpdesk.service.AtribMeniuRolService;
import md.cnam.helpdesk.service.ClCategoriiService;
import md.cnam.helpdesk.service.MeniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/meniu")
public class MenuController {
    @Autowired MeniuService meniuService;
    @Autowired ClCategoriiService categoriiService;
    @Autowired AtribMeniuRolService atribMeniuRolService;
    @Autowired MenuGuest menuGuest;
    @Autowired ObjectMapperM4 mapper;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getMenu() {

        return "admin.meniu";
    }
    
    @RequestMapping(value="/gridlist",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String gridlist(
            HttpServletRequest request
        ){
        JqxTreeGridModel gridModel=new JqxTreeGridModel();
        meniuService.fillGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString((Object)gridModel.getData());
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DiviziuniController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value="/list",method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String classification(
            HttpServletRequest request
        ){
        try {
            String json = mapper.writeValueAsString(meniuService.list());
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DiviziuniController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="/edit",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody Boolean edit(
            HttpServletRequest request
        ){
        switch(request.getParameter("action")){
            case "edit":{
                if (!request.getParameter("idMeniu").equals("undefined")) {
                    Meniu meniu=new Meniu();
                    if (request.getParameter("idMeniu").equals("new")) {
                        meniu.setName(request.getParameter("name"));
                        meniu.setParentId(Integer.parseInt(request.getParameter("parent")));
                        meniu.setWeight(Integer.parseInt(request.getParameter("weight")));
                        meniu.setType(request.getParameter("type"));
                        meniu.setPath(request.getParameter("path"));
                        meniuService.save(meniu);
                        menuGuest.clear();
                    }else{
                        meniu.setId(Integer.parseInt(request.getParameter("idMeniu")));
                        meniu.setName(request.getParameter("name"));
                        meniu.setParentId(Integer.parseInt(request.getParameter("parent")));
                        meniu.setWeight(Integer.parseInt(request.getParameter("weight")));
                        meniu.setType(request.getParameter("type"));
                        meniu.setPath(request.getParameter("path"));
                        meniuService.update(meniu);
                        menuGuest.clear();
                    }
                }
                break;
            }
            case "del":{
                if (!request.getParameter("idMeniu").equals("new")) {
                    Meniu meniu=new Meniu(Integer.parseInt(request.getParameter("idMeniu")));
                    meniuService.delete(meniu);
                    menuGuest.clear();
                }
                break;
            }
        }
        
        return true;
    }
    
    @RequestMapping(value = "/atribRol", method = RequestMethod.GET)
    public  String atribRolindex() {
        
        return "admin.atribRol";
    }
    
    @RequestMapping(value = "/atribRolAjax", method = RequestMethod.POST)
    public String atribRolAjaxindex(
            HttpServletRequest request,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "data[role]", required = false) Integer role,
            @RequestParam(value = "data[module][]", required = false) Integer module,
            //@RequestParam("role") String data,
            Model model
    ) {
        switch(action){
            case "setModule":{
                if (role!=null && module!=null) {
                    AtribMeniuRol atribMeniuRol=new AtribMeniuRol();
                    atribMeniuRol.setIdMeniu(module);
                    atribMeniuRol.setIdRol(role);
                    atribMeniuRolService.save(atribMeniuRol);
                    menuGuest.clear();
                }
                break;
            }
            case "removeModule":{
                if (role!=null && module!=null) {
                    AtribMeniuRol atribMeniuRol=new AtribMeniuRol();
                    atribMeniuRol.setIdMeniu(module);
                    atribMeniuRol.setIdRol(role);
                    atribMeniuRolService.delete(role,module);
                    menuGuest.clear();
                }
                break;
            }
        }
        
        model.addAttribute("selrol", role);
        model.addAttribute("roles", categoriiService.list());
        model.addAttribute("deniedModules", meniuService.treeArrayToSelectElem(role));
        model.addAttribute("accessibleModules", atribMeniuRolService.treeArrayToSelectElem(role));
        
        return "admin.atribRolAjax";
    }
        
}
