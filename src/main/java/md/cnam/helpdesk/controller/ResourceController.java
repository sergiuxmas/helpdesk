/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.dao.ResourcesImpl;
import md.cnam.helpdesk.entity.Resources;
import md.cnam.helpdesk.model.JqGridViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resources")
public class ResourceController {
    @Autowired ResourcesImpl resourceDao;
    @Autowired private ObjectMapperM4 mapper;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(
            Model model
    ){
        return "admin.resources";
    }
    
    @RequestMapping(value="/gridlist",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String gridlist(
            HttpServletRequest request
        ){
        JqGridViewModel gridModel=new JqGridViewModel();
        //gridModel.getSearchMap().put("id_categoria", user.getId());
        gridModel.setSidx("id_role");
        gridModel.autocompleteParameter(request,new String[]{"path","id_role"});
        gridModel.setRows(resourceDao.gridlist(gridModel.getCriterya()));

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value="/edit",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String edit(
            HttpServletRequest request
        ){
        switch(request.getParameter("oper")){
            case "add":{
                Resources resourceNew=new Resources();
                resourceNew.setPath(request.getParameter("path"));
                resourceNew.setIdRole(Integer.parseInt(request.getParameter("id_role")));
                resourceDao.save(resourceNew);
                break;
            }
            case "edit":{
                Resources resource=new Resources(Integer.parseInt(request.getParameter("id")), request.getParameter("path"));
                resource.setIdRole(Integer.parseInt(request.getParameter("id_role")));
                resourceDao.update(resource);
                break;
            }
            case "del":{
                Resources resource=new Resources(Integer.parseInt(request.getParameter("id")));
                resourceDao.delete(resource);
                break;
            }
        }
        
        JqGridViewModel gridModel=new JqGridViewModel();
        gridModel.setSidx("id_role");
        gridModel.autocompleteParameter(request,new String[]{"path","id_role"});
        gridModel.setRows(resourceDao.gridlist(gridModel.getCriterya()));

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
