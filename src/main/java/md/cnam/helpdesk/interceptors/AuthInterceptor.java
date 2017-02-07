/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.interceptors;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.model.MenuGuest;
import md.cnam.helpdesk.model.MenuSession;
import md.cnam.helpdesk.model.UserSession;
import md.cnam.helpdesk.service.MeniuService;
import md.cnam.helpdesk.util.MyCollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//se activeaza fara @Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    MeniuService meniuService;
    @Autowired
    MenuGuest menuGuest;
    
    String[] guestPages={"/index","/home","/auth/index","/auth/reg","/user/logout","/notify/auth","/notify/authDoubleClick","/notify/newticket","/notify/tiket/list","/notify/getUser"};
    List<String> accessPages=Arrays.asList(guestPages);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("Pre-handle");
        UserSession sessionUser = (UserSession) request.getSession().getAttribute("user");
        //System.out.println(request.getServletPath()); //! interesant
        if (sessionUser == null) {
            //RequestDispatcher requestDispatcher = request.getRequestDispatcher("authorization");
            //requestDispatcher.forward(request, response);
            //ModelAndView model=new ModelAndView("authorization");
            //throw new ModelAndViewDefiningException(model);
            //System.out.println(request.getRequestURI());  //! interesant 
            //System.out.println(request.getServletPath()); //! interesant
            if (menuGuest.getTreeMenu().size()==0) { 
                /*
                Meniul guest se pastreaza in application context
                Daca nu contine meniu si sesiunea user nu exista, atunci se genereaza meniul
                */
                menuGuest.setContextPath(request.getContextPath());
                meniuService.initMenuGuest(menuGuest);
            }
            if (menuGuest.isAccesiblePage(request.getServletPath())) {
                return true;
            }
            //System.out.println(request.getServletPath());
            
            /*
            cind este deschisa sesiunea USER, se verifica daca este acces la resursa (guestPages)
            daca nu-i acces se redirectioneaza la pagina de logare
            */
            if (!isAjax(request) && !MyCollectionUtil.findInArray(accessPages, request.getServletPath())) {
                response.sendRedirect(request.getContextPath() + "/auth/index");
                return false;
            }

            return true;
        }else{
            //menuGuest.clear();
        }

        MenuSession menuSession = (MenuSession) request.getSession().getAttribute("menu");
        if (menuSession == null) {
            menuSession = new MenuSession();
            if (sessionUser != null) {
                menuSession.setContextPath(request.getContextPath());
                //if (sessionUser.getIdCategoria() != null) {
                    //menuSession.setRol(sessionUser.getIdCategoria().getId());
                    //menuSession.setRolName(sessionUser.getCategoriaNume());
                //}
                meniuService.initMenuSession(menuSession);
                request.getSession().setAttribute("menu", menuSession);
                //System.out.println("ID CATEGORIA="+sessionUser.getIdCategoria().getId());
            }
        } else {
           if(isAjax(request))return true;//Pentru ajax request e deschis accesul
           else{
               if (menuSession.isAccesiblePage(request.getServletPath()) || request.getServletPath().contains("/logout") || request.getServletPath().contains("/auth/index")) {
                   //System.out.println(request.getServletPath()+":TRUE");
                   return true;
                }else{
                   //System.out.println(request.getServletPath()+":FALSE ("+request.getServletPath()+")");
                   response.sendRedirect(request.getContextPath() + "/auth/index");//!Poate intra in ciclu
                   return false;
               }
           }
        }

        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("Post-handle");
        //System.out.println(modelAndView.getViewName());
        
        UserSession sessionUser = (UserSession) request.getSession().getAttribute("user");
        if (sessionUser == null) {
            if (isAjax(request) && !response.isCommitted()) {
                //System.out.println("ajax");
                response.setHeader("Content-Type", "text/plain");
                response.setCharacterEncoding("UTF-8");
                try (PrintWriter writer = response.getWriter()) {
                    writer.write("sessionout");
                }
                response.getOutputStream().flush();
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        
    }
    
    public boolean ifcontains(String str){
        for (String page : accessPages) {
            if (page.equals(str)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }
    
}
