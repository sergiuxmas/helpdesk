/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.util.HashMap;
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
import md.cnam.helpdesk.entity.TiketOpenHistory;
import md.cnam.helpdesk.model.ExecutareFormModel;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.service.ClDomeniiService;
import md.cnam.helpdesk.service.ClPrioritateService;
import md.cnam.helpdesk.service.ClSolicitareTipService;
import md.cnam.helpdesk.service.ClUseriService;
import md.cnam.helpdesk.service.ExecutareService;
import md.cnam.helpdesk.service.ExecutorHistoryService;
import md.cnam.helpdesk.service.FilesService;
import md.cnam.helpdesk.service.MessagesService;
import md.cnam.helpdesk.service.TiketOpenHistoryService;
import md.cnam.helpdesk.service.TiketService;
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
@RequestMapping("/tiket")
public class TiketUserDsiController {
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
    TiketOpenHistoryService tiketOpenService;
    
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
    
    @RequestMapping(value = "/userDSI/responsableHistory", method = RequestMethod.GET)
    @ResponseBody
    public List responsableHistory(
            @RequestParam(value = "idTiket", required = true) Integer idTiket
    ){

        return tiketService.responsableTikets(idTiket);
    }
    
    
    @RequestMapping(value = "/userDSI/view", method = RequestMethod.GET)
    public String viewUserDSI(
            @RequestParam(value = "id", required = true) Integer id,
            @ModelAttribute("executareFormModel") ExecutareFormModel executareModel,
            Model model
    ){
        if (id!=null) {
            Solicitare sol=new Solicitare(id);
            sol.setOpened(true);
            tiketService.updateOpenedTiket(sol);
            tiketOpenService.saveOrUpdate(id);
        }
        Map tiket=tiketService.getTiket(id);
        
        Map executareInfo=executareService.findByIdTiket(id);
        if (executareInfo!=null) {
            executareModel.setPrioritate((Integer) executareInfo.get("id_prioritate"));
            executareModel.setTip((Integer) executareInfo.get("tip_solicitare"));
            executareModel.setDomeniu((Integer) executareInfo.get("id_domeniu"));
            executareModel.setDescriere(String.valueOf(executareInfo.get("descriere")));
        //System.out.println(executareInfo);
        }
        
        model.addAttribute("userList", userService.listDSI());
        model.addAttribute("idSolicitare", id);
        model.addAttribute("tiket", tiket);
        model.addAttribute("prioritateList", prioritateService.list());
        model.addAttribute("tipList", solTipService.list());
        model.addAttribute("domeniuList", domeniiService.list());
        model.addAttribute("executareFormModel", executareModel);
        model.addAttribute("executareList", executareService.listSortDesc(id));
        model.addAttribute("fileList", filesService.getFilesByIdTiket(id));
        return "userDSI.tiket.view";
    }
    
    @RequestMapping(value = "/userDSI/modifyResponsableDSI", method = RequestMethod.POST)
    public String modifyResponsableDSI(
            @RequestParam(value = "idTiket", required = true) Integer idTiket,
            @RequestParam(value = "newExecutorDSI", required = true) Integer newExecutorDSI,
            HttpServletRequest request,
            Model model
    ){
        //ClUseri sessionUser=(ClUseri)httpSession.getAttribute("user");
        if (request.getParameter("currentExecutorDSI")==null && request.getParameter("newExecutorDSI")!=null) {
            applyResponsableDSI(idTiket,newExecutorDSI);
        }else if(request.getParameter("newExecutorDSI").isEmpty()){
            applyResponsableDSI(idTiket,null);
        }
        else if (newExecutorDSI!=Integer.parseInt(request.getParameter("currentExecutorDSI"))) {
           applyResponsableDSI(idTiket,newExecutorDSI);
        }

        Map tiket=tiketService.getTiket(idTiket);
        
        model.addAttribute("userList", userService.listDSI());
        model.addAttribute("idSolicitare", idTiket);
        model.addAttribute("tiket", tiket);
        model.addAttribute("prioritateList", prioritateService.list());
        model.addAttribute("tipList", solTipService.list());
        model.addAttribute("domeniuList", domeniiService.list());
        model.addAttribute("executareFormModel", new ExecutareFormModel());
        return "userDSI.tiket.viewAjax";
    }
    
    @RequestMapping(value = "/userDSI/acceptTiket", method = RequestMethod.POST)
    public String acceptTiket(
            @RequestParam(value = "idTiket", required = true) Integer idTiket,
            @RequestParam(value = "id_executor_history", required = true, defaultValue = "") Integer id_executor_history,
            Model model
    ){
        ClUseri userCurent=(ClUseri)httpSession.getAttribute("user");
        
        if (id_executor_history==null) {
            applyResponsableDSI(idTiket,userCurent.getId());
        }
        
        ExecutorHistory eh=new ExecutorHistory(id_executor_history);
        eh.setUserExecutor(userCurent.getId());
        eh.setUserDecizie(userCurent.getId());
        eh.setIdSolicitare(idTiket);
        ehService.acceptTiket(eh);

        Map tiket=tiketService.getTiket(idTiket);
        model.addAttribute("userList", userService.listDSI());
        model.addAttribute("idSolicitare", idTiket);
        model.addAttribute("tiket", tiket);
        model.addAttribute("prioritateList", prioritateService.list());
        model.addAttribute("tipList", solTipService.list());
        model.addAttribute("domeniuList", domeniiService.list());
        model.addAttribute("executareFormModel", new ExecutareFormModel());
        return "userDSI.tiket.viewAjax";
    }
    
    @RequestMapping(value = "/userDSI/gridlist", method = RequestMethod.GET)
    public String userDSIgridlist(){
        
        return "userDSI.tiket.list";
    }
    
    @ResponseBody
    @RequestMapping(value = "/userDSI/gridlist", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public String userDSIgridlistAjax(
            HttpServletRequest request
    ){
        ClUseri user=(ClUseri)httpSession.getAttribute("user");
        JqGridViewModel gridModel=new JqGridViewModel();
        gridModel.getSearchMap().put("user_executor", user.getId());
        tiketService.fillUserDSIGridModel(request,gridModel);

        try {
            String json = mapper.writeValueAsString(gridModel);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CategoriiController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @RequestMapping(value = "/userDSI/inchidereTiket", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public String executareTiket(
            @RequestParam(value = "idTiket", required = true) Integer idTiket,
            Model model
    ){
        
        Solicitare sol=new Solicitare(idTiket);
        sol.setStatut(3);
        tiketService.updateStatusTiket(sol);
        
        Map tiket=tiketService.getTiket(idTiket);
        Map tiketExecutat=executareService.findByIdTiket(idTiket);
        ExecutareFormModel executareModel=new ExecutareFormModel();
        executareModel.setDescriere(String.valueOf(tiketExecutat.get("descriere")));
        executareModel.setIdTiket(idTiket);
      
        model.addAttribute("userList", userService.listDSI());
        model.addAttribute("idSolicitare", idTiket);
        model.addAttribute("tiket", tiket);
        model.addAttribute("prioritateList", prioritateService.list());
        model.addAttribute("tipList", solTipService.list());
        model.addAttribute("domeniuList", domeniiService.list());
        model.addAttribute("executareFormModel", executareModel);
        return "userDSI.tiket.viewAjax";
    }
    
    @RequestMapping(value = "/userDSI/executareTiket", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"}, headers="Accept=application/json")
    public String executareTiket(
            @Valid @ModelAttribute("executareFormModel") ExecutareFormModel executareModel,
            BindingResult result,
            Model model
    ){
        if(!result.hasErrors()) {
            ClUseri sessionUser=(ClUseri)httpSession.getAttribute("user");
            Executare executare=new Executare();
            executare.setIdPrioritate(executareModel.getPrioritate());
            executare.setTipSolicitare(executareModel.getTip());
            executare.setIdDomeniu(executareModel.getDomeniu());
            executare.setIdSolicitare(executareModel.getIdTiket());
            executare.setDescriere(executareModel.getDescriere());
            executare.setIdExecutor(sessionUser.getId());
            executareService.saveOrUpdate(executare);
        }
        
        Map tiket=tiketService.getTiket(executareModel.getIdTiket());
        //executareModel.setDescriere(String.valueOf(tiket.get("descriere")));
      
        model.addAttribute("userList", userService.listDSI());
        model.addAttribute("idSolicitare", executareModel.getIdTiket());
        model.addAttribute("tiket", tiket);
        model.addAttribute("prioritateList", prioritateService.list());
        model.addAttribute("tipList", solTipService.list());
        model.addAttribute("domeniuList", domeniiService.list());
        model.addAttribute("executareFormModel", executareModel);
        return "userDSI.tiket.viewAjax";
    }
    
    @ResponseBody
    @RequestMapping(value = "/userDSI/saveMessage", method = RequestMethod.POST)
    public void saveMessage(
            @RequestParam(value = "idTiket") Integer idTiket,
            @RequestParam(value = "newmessage") String newmessage
    ){
        ClUseri sessionUser=(ClUseri)httpSession.getAttribute("user");
        Messages newMessage=new Messages();
        newMessage.setIdSolicitare(idTiket);
        newMessage.setMessage(newmessage);
        newMessage.setIdExecutor(sessionUser.getId());
        newMessage.setDataExpedierii(new Date());
        messageService.save(newMessage);
        tiketOpenService.saveOrUpdate(idTiket);
    }
    
    @RequestMapping(value = "/userDSI/messagesList", method = RequestMethod.GET)
    public String messagesList(
            @RequestParam(value = "idTiket") Integer idTiket,
            Model model
    ){
        model.addAttribute("messagesList", messageService.listSortAsc(idTiket));
        return "userDSI.tiket.messagesList";
    }

}
