package md.cnam.helpdesk.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import md.cnam.helpdesk.config.ObjectMapperM4;
import md.cnam.helpdesk.entity.ClDiviziuni;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository//(value="diviziuniDao")
public class ClDiviziuniImpl extends GenericDaoHibImpl<ClDiviziuni>{
            
    @Autowired
    ObjectMapperM4 mapper; //l-am initializat in bean
    
    @Transactional
    public String getByIdJson(int id) {
        String sql = "SELECT * FROM cl_diviziuni where nume<>'root' AND idDiviziune=:idDiviziune";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        //query.addEntity(ClDiviziuni.class);
        query.setParameter("idDiviziune", id);
        Object diviziune = query.uniqueResult();
        
        try {
            //ObjectMapper mapper = new ObjectMapper();
            //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            //mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            return mapper.writeValueAsString(diviziune);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ClDiviziuniImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Transactional
    public String getAllJson(){
        String sql = "SELECT * FROM cl_diviziuni";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.addEntity(ClDiviziuni.class);
        List results = query.list();
        
        try {
            //http://stackoverflow.com/questions/23590475/java-list-to-json-array-using-jackson-with-utf-8-encoding
            //mapper.setSerializationInclusion(Include.NON_NULL);
            //mapper.setSerializationInclusion(Include.NON_EMPTY);
            //mapper.registerModule(new Hibernate4Module());
            String json = mapper.writeValueAsString(results);
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ClDiviziuniImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Transactional
    public List listByCriterya(Map criterya){
        String sql = "SELECT di.idDiviziune as idDiviziune,di.nume as nume,di.fullname as fullname,di.parentId as parentId,di.userDSI as userDSI,u.nume as userDSINume "
                + " FROM cl_diviziuni as di"
                + " LEFT JOIN cl_useri as u "
                + " ON di.userDSI=u.id "
                + " AND di.nume!='root' AND di.nume like IFNULL(null,di.nume) order by di.nume asc LIMIT 0,1000";
        //int parentId=(int)criterya.get("parentId");
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        //query.setParameter("parentId", parentId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        //query.addEntity(ClDiviziuni.class);
        
        List list=query.list();
        //Logger.getLogger(ClDiviziuniImpl.class.getName()).log(Level.INFO, list.toString());
        return list;
    }
    
    @Override
    @Transactional
    public List<ClDiviziuni> list() {
        return session.getCurrentSession().createQuery("from " + getClassName()+" order by parentId,nume").list();
    }
    
    @Transactional
    public List<ClDiviziuni> listByParent(int parentId){
        return session.getCurrentSession().createQuery("from " + getClassName()+" where parentId="+parentId+" order by parentId,nume").list();
    }
}
