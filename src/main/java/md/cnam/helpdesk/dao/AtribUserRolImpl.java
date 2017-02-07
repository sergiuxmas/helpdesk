/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md.cnam.helpdesk.dao;

import md.cnam.helpdesk.entity.AtribUserRol;
import md.cnam.helpdesk.entity.ClCategorii;
import md.cnam.helpdesk.entity.ClUseri;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Repository
public class AtribUserRolImpl extends GenericDaoHibImpl<AtribUserRol> {

    @Transactional
    public void saveOrUpdate(AtribUserRol ur) {
        String sqlUpdate = "call saveOrUpdateUserRol(:id_rol, :id_user);";
        SQLQuery queryUpdate = session.getCurrentSession().createSQLQuery(sqlUpdate);
        queryUpdate.setParameter("id_rol", ur.getIdRol());
        queryUpdate.setParameter("id_user", ur.getIdUser());
        queryUpdate.executeUpdate();
    }
    
    /*
    sterge toate rolurile utilizatorului dat
    */
    @Transactional
    public void delete(Integer idUser) {
        String sqlUpdate = "delete from atrib_user_rol where id_user=:id_user";
        SQLQuery queryUpdate = session.getCurrentSession().createSQLQuery(sqlUpdate);
        queryUpdate.setParameter("id_user", idUser);
        queryUpdate.executeUpdate();
    }

    /*
     posibilitatea de a sterge rolul
     rolul poate sa fie delimitat prin ','
     */
    @Transactional
    public void saveOrUpdateOrDel(String ur, Integer idUser) {
        delete(idUser);
        String[] roluri = StringUtils.delimitedListToStringArray(ur, ",");
        for (String rol : roluri) {
            AtribUserRol newRol = new AtribUserRol();
            newRol.setIdRol(new ClCategorii(Integer.parseInt(rol)));
            newRol.setIdUser(new ClUseri(idUser));
            saveOrUpdate(newRol);
        }
    }
}
