/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.entity.ExecutorHistory;
import md.cnam.helpdesk.entity.Solicitare;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.service.ClDomeniiService;
import md.cnam.helpdesk.service.ClPrioritateService;
import md.cnam.helpdesk.service.ClSolicitareTipService;
import md.cnam.helpdesk.service.ClUseriService;
import md.cnam.helpdesk.service.ExecutareService;
import md.cnam.helpdesk.service.ExecutorHistoryService;
import md.cnam.helpdesk.service.FilesService;
import md.cnam.helpdesk.service.MessagesService;
import md.cnam.helpdesk.service.TiketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tiket")
public class TiketAdminController {
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
    FilesService filesService;
    
    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    ObjectMapperM4 mapper;
    
    public void applyResponsableDSI(Integer idTiket,Integer newExecutorDSI){
        ClUseri sessionUser=(ClUseri)httpSession.getAttribute("user");
        ExecutorHistory eh=new ExecutorHistory();
        eh.setIdSolicitare(idTiket);
        eh.setUserExecutor(newExecutorDSI);
        eh.setUserDecizie(sessionUser.getId());
        ehService.insertRecord(eh);
    }
    
    @RequestMapping(value = "/admin/gridlist", method = RequestMethod.GET)
    public String gridlist(){
        
        return "admin.tiket.list";
    }
    
    @ResponseBody
    @RequestMapping(value = "/admin/gridlist", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public String gridlistAjax(
            HttpServletRequest request
    ){
        
        JqGridViewModel gridModel=new JqGridViewModel();
        tiketService.fillAdminGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value = "/admin/view", method = RequestMethod.GET)
    public String view(
            @RequestParam(value = "id", required = true) Integer id,
            Model model
    ){
        if (id!=null) {
            Solicitare sol=new Solicitare(id);
            sol.setOpened(true);
            tiketService.updateOpenedTiket(sol);
        }
        Map tiket=tiketService.getTiket(id);
        Map executareInfo=executareService.findByIdTiket(id);
        
        model.addAttribute("userList", userService.listDSI());
        model.addAttribute("idSolicitare", id);
        model.addAttribute("tiket", tiket);
        model.addAttribute("executareInfo", executareInfo);
        model.addAttribute("fileList", filesService.getFilesByIdTiket(id));
        return "admin.tiket.view";
    }
    
    @RequestMapping(value = "/admin/modifyResponsableDSI", method = RequestMethod.POST)
    public String modifyResponsableAdmin(
            @RequestParam(value = "idTiket", required = true) Integer idTiket,
            @RequestParam(value = "newExecutorDSI", required = true) Integer newExecutorDSI,
            @RequestParam(value = "currentExecutorDSI", required = true) Integer currentExecutorDSI,
            Model model
    ){
        if (newExecutorDSI!=currentExecutorDSI) {
            applyResponsableDSI(idTiket,newExecutorDSI);
        }
        Map tiket=tiketService.getTiket(idTiket);
        
        model.addAttribute("userList", userService.listDSI());
        model.addAttribute("idSolicitare", idTiket);
        model.addAttribute("tiket", tiket);
        model.addAttribute("prioritateList", prioritateService.list());
        model.addAttribute("tipList", solTipService.list());
        model.addAttribute("domeniuList", domeniiService.list());
        return "admin.tiket.viewAjax";
    }
}
