/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.dao.ClCategoriiImpl;
import md.cnam.helpdesk.entity.ClCategorii;
import md.cnam.helpdesk.model.JqGridViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ClCategoriiService  implements GenericServiceIntf<ClCategorii>{

    @Autowired
    ClCategoriiImpl categoriiDao;
 
    @Override
    public void save(ClCategorii t) {
        categoriiDao.save(t);
    }

    @Override
    public ClCategorii findById(long id) {
        return categoriiDao.findById(id);
    }

    @Override
    public void update(ClCategorii t) {
        categoriiDao.update(t);
    }

    @Override
    public void delete(ClCategorii t) {
        categoriiDao.delete(t);
    }

    @Override
    public List<ClCategorii> list() {
        return categoriiDao.list();
    }
    
    public void fillGridModel(HttpServletRequest request,JqGridViewModel gridModel){
        //gridModel.setSord(request.getParameter("sord"));
        //gridModel.setSidx(request.getParameter("sidx"));
        //gridModel.setPage(request.getParameter("page"));
        //gridModel.setRowsx(request.getParameter("rows"));
        gridModel.setSidx("nume");
        gridModel.autocompleteParameter(request);
        gridModel.setRows(categoriiDao.listByCriterya(gridModel.getCriterya()));
    }
}
