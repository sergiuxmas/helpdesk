/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import md.cnam.helpdesk.entity.Resources;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ResourcesImpl extends GenericDaoHibImpl<Resources>{
    @Transactional
    public List gridlist(Map criterya){
        String sql ="select id, path, id_role "
                + " from resources "
                + " where  "
                + " path like IFNULL(:path,path) "
                + " and id_role=IFNULL(:id_role,id_role) "
                + " order by "+criterya.get("sidx")+" "+criterya.get("sord")
                ;
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        
        Map search=(Map)criterya.get("search");
        String[] params={"path","id_role"};
        Vector<String> likeFilter=new Vector<String>();
        likeFilter.add("path");
        for (String param : params) {
            if (search.containsKey(param)) {
                if (likeFilter.contains(param)) {
                    //System.out.println("contine");
                    query.setParameter(param, '%'+(String)search.get(param)+'%');
                } else query.setParameter(param, String.valueOf(search.get(param)));
            }else{
                query.setParameter(param, null);
            }
        }
        
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
        
    }
}
