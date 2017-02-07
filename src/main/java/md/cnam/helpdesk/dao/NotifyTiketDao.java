/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.model.NotifyUserSession;
import md.cnam.helpdesk.model.UserSession;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NotifyTiketDao {
    @Autowired
    protected SessionFactory session;

    public NotifyTiketDao() {
    }
    /*
    @Transactional
    public List<Map> tiketList(ClUseri user) {
        String sql = "";
        if (user.getIdCategoria().getNume().equals("Moderator")) {
            sql = "SELECT s.id as id,d.nume as domeniu,s.id_prioritate,p.nume as prioritate,u.nume as user,(SELECT nume FROM cl_useri WHERE id=s.user_executor) as user_executor,DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y %H:%i:%s') as data_solicitarii,dv.idDiviziune as id_diviziune,dv.nume as diviziune,s.opened as opened "
                    + " FROM solicitare AS s,cl_domeniu AS d,cl_prioritate AS p,cl_useri AS u,cl_diviziuni as dv "
                    + " WHERE s.id_domeniu=d.id AND s.id_prioritate=p.id AND s.id_user=u.id AND dv.idDiviziune=u.id_diviziune "
                    + " AND s.opened is not true "
                    + " order by s.data_solicitarii desc LIMIT 0,1000";
        } else if (user.getIdCategoria().getNume().equals("IT")) {
            sql="CALL tiketListUserDSI(:id_executor);";
        }else if (user.getIdCategoria().getNume().equals("common")) {
            sql="CALL tiketListUser(:id_executor);";
        }
        
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_executor", user.getId());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        //query.addEntity(Solicitare.class);

        return query.list();
    }
    */
    @Transactional
    public int updateTiket(Map tiket) {
        //System.out.println(tiket);

        String sql = "UPDATE solicitare SET "
                + "opened=IFNULL(:opened,opened)"
                + " WHERE id=:id";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", tiket.get("id"));
        query.setParameter("opened", true);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.executeUpdate();
    }
    
    @Transactional
    public Map getUserIdAndCat(String username) {
        String sql = "SELECT u.id as id,c.nume as categoria from cl_useri as u,cl_categorii as c WHERE u.id_categoria=c.id AND username='" + username + "'";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (Map)query.uniqueResult();
    }

}
