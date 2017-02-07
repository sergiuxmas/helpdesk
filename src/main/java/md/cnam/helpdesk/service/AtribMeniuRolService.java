/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.service;

import java.util.List;
import md.cnam.helpdesk.dao.AtribMeniuRolImpl;
import md.cnam.helpdesk.entity.AtribMeniuRol;
import md.cnam.helpdesk.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtribMeniuRolService implements GenericServiceIntf<AtribMeniuRol>{
    @Autowired
    AtribMeniuRolImpl attrMeniuRolDao;
    @Autowired
    MeniuService meniuService;

    @Override
    public void save(AtribMeniuRol t) {
        attrMeniuRolDao.save(t);
    }

    @Override
    public AtribMeniuRol findById(long id) {
        return attrMeniuRolDao.findById(id);
    }

    @Override
    public void update(AtribMeniuRol t) {
        attrMeniuRolDao.update(t);
    }

    @Override
    public void delete(AtribMeniuRol t) {
        attrMeniuRolDao.delete(t);
    }
    public void delete(Integer role,Integer module) {
        attrMeniuRolDao.delete(role,module);
    }

    @Override
    public List<AtribMeniuRol> list() {
        return attrMeniuRolDao.list();
    }
    
    public String treeArrayToSelectElem(Integer role){
        TreeUtil treeUtil=new TreeUtil(meniuService.listMap());
        treeUtil.arrAppliedModles=attrMeniuRolDao.getMenuByRole(role);
        treeUtil.showAppliedModles=true;
        return treeUtil.treeArrayToSelectElem(1,0);
    }
}
