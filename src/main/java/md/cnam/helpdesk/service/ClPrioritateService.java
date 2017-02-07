/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.ClPrioritateImpl;
import md.cnam.helpdesk.entity.ClPrioritate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ClPrioritateService implements GenericServiceIntf<ClPrioritate>{
    @Autowired
    ClPrioritateImpl prioritateDao;

    @Override
    public void save(ClPrioritate t) {
        prioritateDao.save(t);
    }

    @Override
    public ClPrioritate findById(long id) {
        return prioritateDao.findById(id);
    }

    @Override
    public void update(ClPrioritate t) {
        prioritateDao.update(t);
    }

    @Override
    public void delete(ClPrioritate t) {
        prioritateDao.delete(t);
    }

    @Override
    public List<ClPrioritate> list() {
        return prioritateDao.list();
    }
    
}
