/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import md.cnam.helpdesk.entity.AtribMeniuRol;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AtribMeniuRolImpl extends GenericDaoHibImpl<AtribMeniuRol>{
    
    @Transactional
    public List getMenuByRole(Integer role){
        String sql="select id_meniu as id from atrib_meniu_rol where id_rol=:role";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("role", role);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); 
        return query.list();
    }
    
    @Transactional
    public int delete(Integer role,Integer module){
        String sql="delete from atrib_meniu_rol where id_rol=:role and id_meniu=:module";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("role", role);
        query.setParameter("module", module);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); 
        return query.executeUpdate();
    }
}
