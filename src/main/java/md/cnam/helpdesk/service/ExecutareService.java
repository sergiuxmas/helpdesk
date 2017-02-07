/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.dao.ExecutareImpl;
import md.cnam.helpdesk.entity.Executare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutareService implements GenericServiceIntf<Executare>{
    @Autowired
    ExecutareImpl executareDao;
    
    @Override
    public void save(Executare t) {
        executareDao.save(t);
    }
    
    public void saveOrUpdate(Executare ex){
        executareDao.saveOrUpdate(ex);
    }

    @Override
    public Executare findById(long id) {
        return executareDao.findById(id);
    }

    @Override
    public void update(Executare t) {
        executareDao.update(t);
    }

    @Override
    public void delete(Executare t) {
        executareDao.delete(t);
    }

    @Override
    public List<Executare> list() {
        return executareDao.list();
    }
    
    public List listSortDesc(Integer idTiket){
        return executareDao.listSortDesc(idTiket);
    }
    
    public Map findByIdTiket(int idTiket){
        return executareDao.findByIdTiket(idTiket);
    }
    
}
