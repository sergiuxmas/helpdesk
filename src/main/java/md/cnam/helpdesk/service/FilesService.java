/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.FilesImpl;
import md.cnam.helpdesk.entity.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilesService implements GenericServiceIntf<Files>{
    @Autowired
    FilesImpl filesDao;
    
    @Override
    public void save(Files t) {
        filesDao.save(t);
    }

    @Override
    public Files findById(long id) {
        return filesDao.findById(id);
    }

    @Override
    public void update(Files t) {
        filesDao.update(t);
    }

    @Override
    public void delete(Files t) {
        filesDao.delete(t);
    }

    @Override
    public List<Files> list() {
        return filesDao.list();
    }
    
    public List getFilesByIdTiket(int idTiket){
        return filesDao.getFilesByIdTiket(idTiket);
    }
}
