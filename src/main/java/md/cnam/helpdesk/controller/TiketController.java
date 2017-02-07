/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.entity.Executare;
import md.cnam.helpdesk.entity.ExecutorHistory;
import md.cnam.helpdesk.entity.Messages;
import md.cnam.helpdesk.entity.Solicitare;
import md.cnam.helpdesk.model.ExecutareFormModel;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.model.TiketForm;
import md.cnam.helpdesk.service.ClDomeniiService;
import md.cnam.helpdesk.service.ClPrioritateService;
import md.cnam.helpdesk.service.ClSolicitareTipService;
import md.cnam.helpdesk.service.ClUseriService;
import md.cnam.helpdesk.service.ExecutareService;
import md.cnam.helpdesk.service.ExecutorHistoryService;
import md.cnam.helpdesk.service.MessagesService;
import md.cnam.helpdesk.service.TiketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/tiket")
public class TiketController {
    @Autowired
    TiketService tiketService;
    
    @Autowired
    ClDomeniiService domeniiService;
    
    @Autowired
    ClPrioritateService prioritateService;
    
    @Autowired
    ClUseriService userService;
    
    @Autowired
    ClSolicitareTipService solTipService;
    
    @Autowired
    ExecutorHistoryService ehService;
    
    @Autowired
    ExecutareService executareService;
    
    @Autowired
    MessagesService messageService;
    
    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    
}
