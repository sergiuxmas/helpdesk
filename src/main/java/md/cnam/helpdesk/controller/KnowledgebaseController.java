/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import md.cnam.helpdesk.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/knowledgebase")
public class KnowledgebaseController {
    @Autowired
    private AppConfig appConfig;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(
            //Model model
    ){
        //model.addAttribute("url", appConfig.getKnowledgebaseLocation());
        return "knowledgebase.list";
    }
}
