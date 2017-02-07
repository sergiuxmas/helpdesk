package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.entity.ClDiviziuni;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.model.JqxTreeGridModel;
import md.cnam.helpdesk.service.ClDiviziuniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DiviziuniController {
    @Autowired
    private ClDiviziuniService diviziuniService;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    @RequestMapping(value="/diviziuni",method=RequestMethod.GET)
    public String index(Model model){
        return "admin.diviziuni";
    }
    
   @RequestMapping(value="/diviziuni/gridlist",method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8", headers="Accept=application/json")
    public @ResponseBody String list(
            HttpServletRequest request
        ){
        
        JqxTreeGridModel gridModel=new JqxTreeGridModel();
        diviziuniService.fillGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString((Object)gridModel.getData());
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DiviziuniController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="/diviziuni/list",method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8", headers="Accept=application/json")
    public @ResponseBody String classification(
            HttpServletRequest request
        ){
        try {
            String json = mapper.writeValueAsString(diviziuniService.list());
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DiviziuniController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
    
    @RequestMapping(value="/diviziuni/edit",method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8", headers="Accept=application/json")
    public @ResponseBody Boolean edit(
            HttpServletRequest request
        ){
        
        switch(request.getParameter("action")){
            case "edit":{
                if (!request.getParameter("idDiviziune").equals("undefined")) {
                    ClDiviziuni diviziune=new ClDiviziuni();
                    if (request.getParameter("idDiviziune").equals("new")) {
                        diviziune.setNume(request.getParameter("nume"));
                        diviziune.setFullname(request.getParameter("fullname"));
                        diviziune.setParentId(Integer.parseInt(request.getParameter("parent")));
                        diviziuniService.save(diviziune);
                    }else{
                        diviziune.setIdDiviziune(Integer.parseInt(request.getParameter("idDiviziune")));
                        diviziune.setNume(request.getParameter("nume"));
                        diviziune.setFullname(request.getParameter("fullname"));
                        diviziune.setParentId(Integer.parseInt(request.getParameter("parent")));
                        if (request.getParameter("userDSI").isEmpty()) {
                            diviziune.setUserDSI(null);
                        }
                        else diviziune.setUserDSI(Integer.parseInt(request.getParameter("userDSI")));
                        diviziuniService.update(diviziune);
                    }
                }
                break;
            }
            case "del":{
                if (!request.getParameter("idDiviziune").equals("new")) {
                    ClDiviziuni diviziune=new ClDiviziuni(Integer.parseInt(request.getParameter("idDiviziune")));
                    diviziuniService.delete(diviziune);
                    }
                break;
            }
        }
        
        return true;
    }
}
