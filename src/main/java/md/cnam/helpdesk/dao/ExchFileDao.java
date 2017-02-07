/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import md.cnam.helpdesk.entity.ExchFiles;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ExchFileDao extends GenericDaoHibImpl<ExchFiles>{
    
    @Transactional
    public List gridlist(Map criterya){
        String sql =""
                + "select f.id,f.id_tema as id_tema,f.id_user,u.nume as username,f.filename,f.extention,f.date_upload as date_upload "
                + "from exch_files f, cl_useri u "
                + "where f.id_user=u.id "
                + " and f.id_tema=:id_tema "
                + " and f.id_user=:id_sessionUser "
                + " and f.filename like IFNULL(:filename,f.filename) "
                + " and DATE_FORMAT(f.date_upload,'%d.%m.%Y')=IFNULL(DATE_FORMAT(STR_TO_DATE(:date_upload, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(f.date_upload,'%d.%m.%Y'))"
                + " order by "+criterya.get("sidx")+" "+criterya.get("sord");
        
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        
        Map search=(Map)criterya.get("search");
        String[] params={"id_tema","id_sessionUser","filename","date_upload"};
        Vector<String> likeFilter=new Vector<String>();
        likeFilter.add("filename");
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
    public List sharedGridlist(Map criterya){
        String sql =""
                + "select f.id,f.id_tema as id_tema,f.id_user,u.nume as username,f.filename,f.extention,f.date_upload as date_upload "
                + "from exch_files f, cl_useri u "
                + "where f.id_user=u.id "
                + " and f.id_tema=:id_tema "
                + " and f.filename like IFNULL(:filename,f.filename) "
                + " and DATE_FORMAT(f.date_upload,'%d.%m.%Y')=IFNULL(DATE_FORMAT(STR_TO_DATE(:date_upload, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(f.date_upload,'%d.%m.%Y'))"
                + " order by "+criterya.get("sidx")+" "+criterya.get("sord");
        
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        
        Map search=(Map)criterya.get("search");
        String[] params={"id_tema","filename","date_upload"};
        Vector<String> likeFilter=new Vector<String>();
        likeFilter.add("filename");
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
    public List<Map> getFilesByIdTheme(int idTheme){
        String sql="select * from exch_files where id_tema=:tema";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("tema", idTheme);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
}
