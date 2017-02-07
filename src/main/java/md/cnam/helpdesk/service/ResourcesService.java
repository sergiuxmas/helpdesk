/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.ResourcesImpl;
import md.cnam.helpdesk.entity.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourcesService implements GenericServiceIntf<Resources>{
    @Autowired ResourcesImpl resourceDao;
    @Override
    public void save(Resources t) {
        resourceDao.save(t);
    }

    @Override
    public Resources findById(long id) {
        return resourceDao.findById(id);
    }

    @Override
    public void update(Resources t) {
        resourceDao.update(t);
    }

    @Override
    public void delete(Resources t) {
        resourceDao.delete(t);
    }

    @Override
    public List<Resources> list() {
        return resourceDao.list();
    }
    
}
