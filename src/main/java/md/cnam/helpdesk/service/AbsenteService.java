/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.AbsenteImpl;
import md.cnam.helpdesk.entity.Absente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsenteService implements GenericServiceIntf<Absente>{
    @Autowired
    AbsenteImpl absenteDao;
    
    @Override
    public void save(Absente t) {
        absenteDao.save(t);
    }

    @Override
    public Absente findById(long id) {
        return absenteDao.findById(id);
    }

    @Override
    public void update(Absente t) {
        absenteDao.update(t);
    }

    @Override
    public void delete(Absente t) {
        absenteDao.delete(t);
    }

    @Override
    public List<Absente> list() {
        return absenteDao.list();
    }
    
}
