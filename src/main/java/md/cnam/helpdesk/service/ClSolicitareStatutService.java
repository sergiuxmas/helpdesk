/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.ClSolicitareStatutImpl;
import md.cnam.helpdesk.entity.ClSolicitareStatut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClSolicitareStatutService implements GenericServiceIntf<ClSolicitareStatut> {
    @Autowired
    ClSolicitareStatutImpl statutDao;

    @Override
    public void save(ClSolicitareStatut t) {
        statutDao.save(t);
    }

    @Override
    public ClSolicitareStatut findById(long id) {
        return statutDao.findById(id);
    }

    @Override
    public void update(ClSolicitareStatut t) {
        statutDao.update(t);
    }

    @Override
    public void delete(ClSolicitareStatut t) {
        statutDao.delete(t);
    }

    @Override
    public List<ClSolicitareStatut> list() {
        return statutDao.list();
    }
}
