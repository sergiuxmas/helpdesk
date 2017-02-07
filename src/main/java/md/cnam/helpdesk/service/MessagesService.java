/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.MessagesImpl;
import md.cnam.helpdesk.entity.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class MessagesService implements GenericServiceIntf<Messages>{

    @Autowired
    MessagesImpl messageDao;
    
    @Override
    public void save(Messages t) {
        messageDao.save(t);
    }

    @Override
    public Messages findById(long id) {
        return messageDao.findById(id);
    }

    @Override
    public void update(Messages t) {
        messageDao.update(t);
    }

    @Override
    public void delete(Messages t) {
        messageDao.delete(t);
    }

    @Override
    public List<Messages> list() {
        return messageDao.list();
    }
    
    public List listSortAsc(Integer idTiket){
        return messageDao.listSortAsc(idTiket);
    }
}
