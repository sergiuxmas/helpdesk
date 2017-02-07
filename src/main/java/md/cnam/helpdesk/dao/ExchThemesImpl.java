/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import md.cnam.helpdesk.entity.ExchThemes;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ExchThemesImpl extends GenericDaoHibImpl<ExchThemes> {
    
    @Transactional
    public List gridlist(Map criterya){
        String sql ="select id, name, id_user, date_created, active "
                + " from exch_themes "
                + " where id_user=:id_sessionUser "
                + " and name like IFNULL(:name,name) "
                //+ " and active=IFNULL(:active,active) "
                + " and DATE_FORMAT(date_created,'%d.%m.%Y')=IFNULL(DATE_FORMAT(STR_TO_DATE(:date_created, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(date_created,'%d.%m.%Y')) "
                + " order by "+criterya.get("sidx")+" "+criterya.get("sord")
                ;
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        
        Map search=(Map)criterya.get("search");
        String[] params={"id_sessionUser","name","date_created"/*,"active"*/};
        Vector<String> likeFilter=new Vector<String>();
        likeFilter.add("name");
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
    
    @Transactional
    public List sharedDocs(Map criterya){
        String sql="select\n" +
        "        t.id,\n" +
        "        t.name,\n" +
        "        u.nume,\n" +
        "        t.date_created as date_created,\n" +
        "        t.active  \n" +
        "    from\n" +
        "        exch_themes t,\n" +
        "        cl_useri u\n" +
        "    where t.active=true "+
                " and u.id = t.id_user\n"+
                " and t.name like IFNULL(:name,t.name)"+
                " and u.nume like IFNULL(:nume,u.nume)"+
                " and DATE_FORMAT(t.date_created,'%d.%m.%Y')=IFNULL(DATE_FORMAT(STR_TO_DATE(:date_created, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(t.date_created,'%d.%m.%Y')) "+
        "    order by\n" +
        " "+criterya.get("sidx")+" "+criterya.get("sord");
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        
        Map search=(Map)criterya.get("search");
        String[] params={"name","nume","date_created"};
        Vector<String> likeFilter=new Vector<String>();
        likeFilter.add("name");
        likeFilter.add("nume");
        for (String param : params) {
            if (search.containsKey(param)) {
                if (likeFilter.contains(param)) {
                    query.setParameter(param, '%'+(String)search.get(param)+'%');
                } else query.setParameter(param, String.valueOf(search.get(param)));
            }else{
                query.setParameter(param, null);
            }
        }
        
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    @Transactional
    public int delete(int idTheme){
        String sql="delete from exch_themes where id=:id";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", idTheme);
        query.executeUpdate();
        
        sql="delete from exch_files where id_tema=:id";
        query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", idTheme);
        return query.executeUpdate();
    }
}
