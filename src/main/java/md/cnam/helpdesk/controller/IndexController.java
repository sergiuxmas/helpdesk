package md.cnam.helpdesk.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @RequestMapping(value={"/index","/home"},method = RequestMethod.GET)
    public String index(
            HttpSession httpSession
    ){
        //System.out.println("Aici: "+httpSession.getAttribute("user"));
        
        return "default";
    }
}
