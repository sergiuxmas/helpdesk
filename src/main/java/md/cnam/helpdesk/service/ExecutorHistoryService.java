/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.ExecutorHistoryImpl;
import md.cnam.helpdesk.entity.ExecutorHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutorHistoryService implements GenericServiceIntf<ExecutorHistory>{
    @Autowired
    ExecutorHistoryImpl ehDao;
    
    @Override
    public void save(ExecutorHistory t) {
        ehDao.save(t);
    }

    @Override
    public ExecutorHistory findById(long id) {
        return ehDao.findById(id);
    }

    @Override
    public void update(ExecutorHistory t) {
        ehDao.update(t);
    }

    @Override
    public void delete(ExecutorHistory t) {
        ehDao.delete(t);
    }

    @Override
    public List<ExecutorHistory> list() {
        return ehDao.list();
    }
    
    public int insertRecord(ExecutorHistory eh){
        return ehDao.insertRecord(eh);
    }
    
    public int acceptTiket(ExecutorHistory eh){
        return ehDao.acceptTiket(eh);
    }
    
}
