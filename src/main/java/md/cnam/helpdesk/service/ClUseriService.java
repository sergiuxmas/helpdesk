/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import md.cnam.helpdesk.dao.AtribUserRolImpl;
import md.cnam.helpdesk.dao.ClUseriImpl;
import md.cnam.helpdesk.dao.GenericDaoHibImpl;
import md.cnam.helpdesk.entity.ClDiviziuni;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClUseriService implements GenericServiceIntf<ClUseri>{
    @Autowired private ClUseriImpl userDao;
    @Autowired private HttpSession httpSession;
    @Autowired private AtribUserRolImpl atribUserRolDao;
    
    public Map findByIdLogin(long idLogin){
        return userDao.findByIdLogin(idLogin);
    }
    
    public Map findByLoginAndPassword(String username, String password){
        return userDao.findByLoginAndPassword(username, password);
    }
         
    /*
    public Map getUserByIdLogin(long idLogin){
        return userDao.getUserByIdLogin(idLogin);
    }
    */
    @Override
    public void save(ClUseri t) {
        userDao.save(t);
    }

    @Override
    public ClUseri findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public void update(ClUseri t) {
        userDao.update(t);
    }

    @Override
    public void delete(ClUseri t) {
        userDao.delete(t);
    }

    @Override
    public List<ClUseri> list() {
        return userDao.list();
    }
    
     public void fillGridModel(HttpServletRequest request,JqGridViewModel gridModel){
        //gridModel.setSord(request.getParameter("sord"));
        //gridModel.setSidx(request.getParameter("sidx"));
        //gridModel.setPage(request.getParameter("page"));
        //gridModel.setRowsx(request.getParameter("rows"));
        gridModel.setSidx("nume");
        gridModel.autocompleteParameter(request);
        gridModel.setRows(userDao.listByCriterya(gridModel.getCriterya()));
        
        //gridModel.setRows(userDao.list());
    }
     
     public void fillNeatasatiGridModel(HttpServletRequest request,JqGridViewModel gridModel){
        gridModel.setSidx("nume");
        gridModel.autocompleteParameter(request);
        gridModel.setRows(userDao.listNeatasatiByCriterya(gridModel.getCriterya()));
    }
     
     public int customUpdate(ClUseri user){
         int rez=userDao.customUpdate(user);
         return rez;
     }
     
     public int customUpdateWithSession(UserSession user){
         int rez=userDao.customUpdate(user);
         if (rez!=0) {
           updateUserSession(user);  
         }
         return rez;
     }
     
     public List listDSI(){
         return userDao.listDSI();
     }
     
     public void updateUserSession(UserSession user){
         httpSession.setAttribute("user", user);
     }

}
