/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.service.AbsenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AbsenteController {
    @Autowired
    AbsenteService absenteService;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    @RequestMapping(value="/regAbsente",method = RequestMethod.GET)
    public String index(){
        
        return "admin.regAbsente";
    }
    
    @RequestMapping(value="/regAbsente/gridlist",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String list(
            HttpServletRequest request
        ){
        
        return "";
    }
    
    @RequestMapping(value="/regAbsente/edit",method = {RequestMethod.POST}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String edit(
            HttpServletRequest request
        ){
        
        return "";
    }
}
