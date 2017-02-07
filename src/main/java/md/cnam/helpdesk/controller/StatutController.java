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
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.service.ClSolicitareStatutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatutController {
    @Autowired
    ClSolicitareStatutService statutService;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    @RequestMapping(value="/statut/list",produces = "application/json", headers="Accept=application/json")
    @ResponseBody public String listAjax(){
        try {
            String json = mapper.writeValueAsString(statutService.list());
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
