/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import md.cnam.helpdesk.dao.ClDomeniiImpl;
import md.cnam.helpdesk.entity.ClDomeniu;
import md.cnam.helpdesk.model.JqGridViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ClDomeniiService implements GenericServiceIntf<ClDomeniu>{
    @Autowired
    ClDomeniiImpl domeniiDao;

    @Override
    public void save(ClDomeniu t) {
        domeniiDao.save(t);
    }

    @Override
    public ClDomeniu findById(long id) {
        return domeniiDao.findById(id);
    }

    @Override
    public void update(ClDomeniu t) {
        domeniiDao.update(t);
    }

    @Override
    public void delete(ClDomeniu t) {
        domeniiDao.delete(t);
    }

    @Override
    public List<ClDomeniu> list() {
        return domeniiDao.list();
    }
    
    public void fillGridModel(HttpServletRequest request,JqGridViewModel gridModel){
        gridModel.setSidx("nume");
        gridModel.autocompleteParameter(request);
        gridModel.setRows(domeniiDao.listByCriterya(gridModel.getCriterya()));
    }
    
}
