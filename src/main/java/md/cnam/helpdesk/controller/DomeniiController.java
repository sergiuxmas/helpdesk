
package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.entity.ClDomeniu;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.service.ClDomeniiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Admin
 */
@Controller
public class DomeniiController {
    @Autowired
    ClDomeniiService domeniuService;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    @RequestMapping(value="/domenii")
    public String list(){
        
        return "admin.domenii";
    }
    
    @RequestMapping(value="/domenii/gridlist",method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8", headers="Accept=application/json")
    public @ResponseBody String list(
            HttpServletRequest request
        ){
        JqGridViewModel gridModel=new JqGridViewModel();
        domeniuService.fillGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value="/domenii/list",method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8", headers="Accept=application/json")
    public @ResponseBody String listAjax(
            HttpServletRequest request
        ){

        try {
            String json = mapper.writeValueAsString(domeniuService.list());
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DomeniiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="/domenii/edit",method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8", headers="Accept=application/json")
    public @ResponseBody String edit(
            HttpServletRequest request
        ){
        switch(request.getParameter("oper")){
            case "add":{
                ClDomeniu domeniuNew=new ClDomeniu();
                domeniuNew.setNume(request.getParameter("nume"));
                domeniuService.save(domeniuNew);
                break;
            }
            case "edit":{
                ClDomeniu domeniu=new ClDomeniu(Integer.parseInt(request.getParameter("id")), request.getParameter("nume"));
                domeniuService.update(domeniu);
                break;
            }
            case "del":{
                ClDomeniu domeniu=new ClDomeniu(Integer.parseInt(request.getParameter("id")));
                domeniuService.delete(domeniu);
                break;
            }
        }
        
        JqGridViewModel gridModel=new JqGridViewModel();
        domeniuService.fillGridModel(request,gridModel);

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
