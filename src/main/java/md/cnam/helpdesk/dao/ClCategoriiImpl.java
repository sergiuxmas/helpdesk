package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.entity.ClCategorii;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class ClCategoriiImpl extends GenericDaoHibImpl<ClCategorii>{
    
    @Transactional
    public List listByCriterya(Map criterya){
        String sql = "SELECT id,nume FROM cl_categorii WHERE nume like IFNULL(:nume,nume) order by "+criterya.get("sidx")+" "+criterya.get("sord")+" LIMIT 0,1000";
        //sql.replace(":sidx", (String)criterya.get("sidx"));
        //sql.replace(":sord", (String)criterya.get("sord"));
        //query.setString(0, "desc");
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        //query.addEntity(ClCategorii.class);
        
        Map search=(Map)criterya.get("search");
        if (search.containsKey("nume")) {
            query.setParameter("nume", '%'+(String)search.get("nume")+'%');
        }else{
            query.setParameter("nume", null);
        }

        return query.list();
    }

    // adaog order by name
    @Override
    @Transactional
    public List<ClCategorii> list() {
        String sql = "SELECT id,nume FROM cl_categorii order by nume asc";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.addEntity(ClCategorii.class);
        //query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
}
