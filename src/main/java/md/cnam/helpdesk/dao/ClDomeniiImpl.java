/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.entity.ClCategorii;
import md.cnam.helpdesk.entity.ClDomeniu;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
public class ClDomeniiImpl extends GenericDaoHibImpl<ClDomeniu>{
   @Transactional
    public List listByCriterya(Map criterya){
        String sql = "SELECT id,nume FROM cl_domeniu WHERE nume like IFNULL(:nume,nume) order by "+criterya.get("sidx")+" "+criterya.get("sord")+" LIMIT 0,1000";
        //sql.replace(":sidx", (String)criterya.get("sidx"));
        //sql.replace(":sord", (String)criterya.get("sord"));
        //query.setString(0, "desc");
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.addEntity(ClCategorii.class);
        
        Map search=(Map)criterya.get("search");
        if (search.containsKey("nume")) {
            query.setParameter("nume", '%'+(String)search.get("nume")+'%');
        }else{
            query.setParameter("nume", null);
        }

        return query.list();
    } 
}
