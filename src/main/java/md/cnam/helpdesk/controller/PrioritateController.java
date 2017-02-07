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
import md.cnam.helpdesk.service.ClPrioritateService;
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
public class PrioritateController {
    @Autowired
    private ClPrioritateService prioritateService;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    @RequestMapping(value="/prioritate/list",method = {RequestMethod.GET}, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String listAjax(
            HttpServletRequest request
        ){

        try {
            String json = mapper.writeValueAsString(prioritateService.list());
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //return "{\"rows\":"+categoriiService.getAllJson()+"}"; 
    }
}
