/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.TiketOpenHistoryDao;
import md.cnam.helpdesk.entity.TiketOpenHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiketOpenHistoryService implements GenericServiceIntf<TiketOpenHistory>{
    @Autowired
    TiketOpenHistoryDao tiketOpenDao;
    
    @Override
    public void save(TiketOpenHistory t) {
        tiketOpenDao.save(t);
    }

    @Override
    public TiketOpenHistory findById(long id) {
        return tiketOpenDao.findById(id);
    }

    @Override
    public void update(TiketOpenHistory t) {
        tiketOpenDao.update(t);
    }

    @Override
    public void delete(TiketOpenHistory t) {
        tiketOpenDao.delete(t);
    }

    @Override
    public List<TiketOpenHistory> list() {
        return tiketOpenDao.list();
    }
    
    public void saveOrUpdate(int idTiket){
        tiketOpenDao.saveOrUpdate(idTiket);
    }
    
}
