/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.notify;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.service.AuthenticationService;
import md.cnam.helpdesk.service.ClUseriService;
import md.cnam.helpdesk.util.Md5;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/notify")
public class NotifyController {
    @Autowired
    AuthenticationService authService;
    
    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    ClUseriService userService;
    
    @RequestMapping(value="/auth", method = RequestMethod.POST)
    @ResponseBody public Boolean auth(
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            @RequestParam(value = "password", required = false, defaultValue = "") String password
    ){
        BasicTextEncryptor bte=new BasicTextEncryptor();
        bte.setPassword("#notify#DSIeTauth");
        try {
            if (authService.authenticate(bte.decrypt(username),bte.decrypt((password)))) {
                return true;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NotifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @RequestMapping(value="/authDoubleClick", method = RequestMethod.GET)
    public String authDoubleClickApp(
            @RequestParam(value = "id", required = false, defaultValue = "") int id,
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            @RequestParam(value = "password", required = false, defaultValue = "") String password,
            HttpServletResponse httpServletResponse,
            HttpServletRequest request,
            HttpServletResponse response
            
    ) throws FileNotFoundException{
        BasicTextEncryptor bte=new BasicTextEncryptor();
        bte.setPassword("#notify#DSIeTauth");
        boolean authenticated =authService.authenticate(username,bte.decrypt(password));
        if (authenticated) {
           ClUseri userSession=(ClUseri)httpSession.getAttribute("user");
           ClUseri userData=userService.findById(userSession.getId());
            //if (userData.getIdCategoria().getNume().equals("IT")) {
                //return "redirect:/tiket/userDSI/view?id="+id;
            //}else if (userData.getIdCategoria().getNume().equals("common")) {
                //return "redirect:/tiket/user/view?id="+id;
            //}else if (userData.getIdCategoria().getNume().equals("Moderator")) {
                //return "redirect:/tiket/admin/view?id="+id;
            //}
           
           //httpServletResponse.sendRedirect("http://help.cnam.md/helpdesk/tiket/userDSI/gridlist?id="+id);
           //response.isCommitted();    
           //return "redirect:http://help.cnam.md/helpdesk/tiket/userDSI/gridlist?id="+id;
           //request.getRequestDispatcher("/abs.jsp").forward(request, response);
           //return "forward:/authRedirectApp";
           //return "authRedirect"; 
           //return "userDSI.tiket.list";
            
        }
        return "authorization";
    }
    
    @RequestMapping(value="/newticket", method = RequestMethod.GET)
    public String newticketApp(
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            @RequestParam(value = "password", required = false, defaultValue = "") String password
            
    ) {
        
        try {
            BasicTextEncryptor bte=new BasicTextEncryptor();
            bte.setPassword("#notify#DSIeTauth");
            boolean authenticated =authService.authenticate(username,bte.decrypt(password));
            if (authenticated) {
                ClUseri userSession=(ClUseri)httpSession.getAttribute("user");
                ClUseri userData=userService.findById(userSession.getId());
                //if (userData.getIdCategoria().getNume().equals("common")) {
                    //return "redirect:/tiket/new";
                //}
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NotifyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "redirect:/auth/index";
    }

}
