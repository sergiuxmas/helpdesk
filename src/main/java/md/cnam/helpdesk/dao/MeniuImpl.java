/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.entity.Meniu;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MeniuImpl extends GenericDaoHibImpl<Meniu>{
    @Transactional
    public List listByCriterya(Map criterya){
        //String sql = "SELECT id,name,parentId,weight FROM meniu WHERE name like IFNULL(:name,name) order by "+criterya.get("sidx")+" "+criterya.get("sord")+" LIMIT 0,1000";
        String sql = "SELECT id as idMeniu,name,fullname,parentId,path,weight,type "
                + " FROM meniu"
                + " WHERE name like IFNULL(null,name) order by weight asc, name asc LIMIT 0,1000";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        return query.list();
    }
    
    @Override
    @Transactional
    public List list() {// pentru jqwidgets, deoarece id nu vede
        String sql = "SELECT id as idMeniu,name,fullname,parentId,weight,type "
                + " FROM meniu"
                + " order by weight asc, name asc LIMIT 0,1000";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        //query.addEntity(Meniu.class);
        return query.list();
    }
    
    @Transactional
    public List listMap() {
        String sql = "SELECT id,name,fullname,parentId,weight,path,type "
                + " FROM meniu"
                + " order by weight asc, name asc LIMIT 0,1000";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        return query.list();
    }
    
    @Transactional
    public List listGuestMap() {
        String sql = "select m.id,m.parentId,m.weight,m.name,m.path,m.description,m.type \n" +
                    " from atrib_meniu_rol as atrib, meniu as m, cl_categorii as cat\n" +
                    " where atrib.id_meniu = m.id\n" +
                    " and atrib.id_rol=cat.id\n" +
                    " and cat.nume=\"guest\"\n" +
                    " order by m.weight asc, m.name asc LIMIT 0,1000";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    @Transactional
    public List listAuthuserMap(Integer idUser) {
        String sql = "CALL getMenu(IFNULL(:id_user,null));";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_user", idUser);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    @Transactional
    public void delete(Meniu t) {
        super.delete(t); //To change body of generated methods, choose Tools | Templates.
        String sql = "delete "+
                     " from meniu " +
                     " where parentId = :id";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", t.getId());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        query.executeUpdate();
    }
    
    
}
