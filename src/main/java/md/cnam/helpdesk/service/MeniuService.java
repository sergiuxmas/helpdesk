/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import md.cnam.helpdesk.dao.AtribMeniuRolImpl;
import md.cnam.helpdesk.dao.MeniuImpl;
import md.cnam.helpdesk.entity.Meniu;
import md.cnam.helpdesk.model.JqGridViewModel;
import md.cnam.helpdesk.model.JqxTreeGridModel;
import md.cnam.helpdesk.model.MenuGuest;
import md.cnam.helpdesk.model.MenuSession;
import md.cnam.helpdesk.model.UserSession;
import md.cnam.helpdesk.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeniuService implements GenericServiceIntf<Meniu>{
    @Autowired MeniuImpl meniuDao;
    @Autowired AtribMeniuRolImpl attrMeniuRolDao;
    @Autowired HttpSession httpSession;

    @Override
    public void save(Meniu t) {
        meniuDao.save(t);
    }

    @Override
    public Meniu findById(long id) {
        return meniuDao.findById(id);
    }

    @Override
    public void update(Meniu t) {
        meniuDao.update(t);
    }

    @Override
    public void delete(Meniu t) {
        meniuDao.delete(t);
    }

    @Override
    public List<Meniu> list() {
        return meniuDao.list();
    }
    
    public List listMap() {
        return meniuDao.listMap();
    }
    
    public void fillGridModel(HttpServletRequest request,JqxTreeGridModel gridModel){
        gridModel.autocompleteParameter(request);
        gridModel.loadData(meniuDao.listByCriterya(gridModel.getCriterya()));
    }
    
    /*
    pentru administrarea meniului /atribRolAjax
    */
    public String treeArrayToSelectElem(Integer role){
        TreeUtil treeUtil=new TreeUtil(listMap());
        treeUtil.arrAppliedModles=attrMeniuRolDao.getMenuByRole(role);
        treeUtil.hideAppliedModles=true;
        return treeUtil.treeArrayToSelectElem(1,0);
    }
    /*
    public List treeArray(){
        TreeUtil treeUtil=new TreeUtil(meniuDao.listAuthuserMap(null));
        return treeUtil.treeArray(1,0);
    }
    */
    public void initMenuSession(MenuSession menuModel){
        List treeMenu=menuModel.getTreeMenu();
        UserSession sessionUser = (UserSession)httpSession.getAttribute("user");
        treeMenu=meniuDao.listAuthuserMap(sessionUser.getId());
        menuModel.setTreeMenu(treeMenu);
        menuModel.setHtmlMenu(render(menuModel));
    }
    
    public void initMenuGuest(MenuGuest menuModel){
        List treeMenu=menuModel.getTreeMenu();
        treeMenu=meniuDao.listGuestMap();
        menuModel.setTreeMenu(treeMenu);
        menuModel.setHtmlMenu(render(menuModel));
    }

    public String render(MenuSession menuModel){
        TreeUtil treeUtil=new TreeUtil(listMap());
        treeUtil.arrAppliedModles=menuModel.getTreeMenu();
        treeUtil.showAppliedModles=true;
        treeUtil.contextPath=menuModel.getContextPath();
        return treeUtil.renderMenu(1, 0);
    }
    
    public String render(MenuGuest menuModel){
        TreeUtil treeUtil=new TreeUtil(listMap());
        treeUtil.arrAppliedModles=menuModel.getTreeMenu();
        treeUtil.showAppliedModles=true;
        treeUtil.contextPath=menuModel.getContextPath();
        return treeUtil.renderMenu(1, 0);
    }
    
}