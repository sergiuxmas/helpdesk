/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import md.cnam.helpdesk.entity.Messages;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
public class MessagesImpl extends GenericDaoHibImpl<Messages>{
    
    @Transactional
    public List listSortAsc(Integer idTiket){
        String sql="SELECT u.nume as user,m.message as message,DATE_FORMAT(m.data_expedierii,'%d.%m.%Y %H:%i:%s') as data_expedierii FROM messages as m,cl_useri as u WHERE m.id_executor=u.id AND m.id_solicitare=:id_tiket ORDER BY m.data_expedierii ASC";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_tiket", idTiket);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
}
