/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.ClSolicitareTipImpl;
import md.cnam.helpdesk.entity.ClSolicitareTip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ClSolicitareTipService implements GenericServiceIntf<ClSolicitareTip>{
    @Autowired
    ClSolicitareTipImpl solTipDao;
    
    @Override
    public void save(ClSolicitareTip t) {
        solTipDao.save(t);
    }

    @Override
    public ClSolicitareTip findById(long id) {
        return solTipDao.findById(id);
    }

    @Override
    public void update(ClSolicitareTip t) {
        solTipDao.update(t);
    }

    @Override
    public void delete(ClSolicitareTip t) {
        solTipDao.delete(t);
    }

    @Override
    public List<ClSolicitareTip> list() {
        return solTipDao.list();
    }
    
}
