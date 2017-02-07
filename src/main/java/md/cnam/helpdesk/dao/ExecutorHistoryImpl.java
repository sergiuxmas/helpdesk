/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import md.cnam.helpdesk.entity.ExecutorHistory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ExecutorHistoryImpl extends GenericDaoHibImpl<ExecutorHistory>{
    
    @Transactional
    public List responsableTikets(int idTiket){
        String sql="select "
                + " h.id as id,"
                + " h.acceptat as acceptat,"
                + " DATE_FORMAT(h.data_acceptare,'%d.%m.%Y %H:%i:%s') as data_acceptare,"
                + " (select nume from cl_useri where id=h.user_executor) as user_executor,"
                + " IFNULL((select nume from cl_useri where id=h.user_decizie),\"SI\") as user_decizie,"
                + " DATE_FORMAT(h.data_decizie,'%d.%m.%Y %h:%i:%s') as data_decizie from executor_history as h "
                + " LEFT JOIN cl_useri as u" +
                    "  on" +
                    "  u.id=h.user_executor WHERE "  +
                    "  h.id_solicitare=:id_solicitare " +
                    "  order by h.data_decizie desc";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_solicitare", idTiket);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    @Transactional
    public int insertRecord(ExecutorHistory eh){
        String sql="insert into executor_history (id_solicitare,user_executor,user_decizie,data_decizie) values (:solicitare,:user_executor,:user_decizie,NOW())";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("solicitare", eh.getIdSolicitare());
        query.setParameter("user_executor", eh.getUserExecutor());
        query.setParameter("user_decizie", eh.getUserDecizie());
        return query.executeUpdate();
    }
    
    @Transactional
    public int acceptTiket(ExecutorHistory eh){
        System.out.println("OOOOOOOOOOO:");
        System.out.println("id="+eh.getId());
        System.out.println("solicitare:"+eh.getIdSolicitare());
        String sql_1="UPDATE executor_history SET acceptat=1,data_acceptare=NOW(),user_executor=:user_executor WHERE id=:id ";
        String sql_2="UPDATE solicitare SET statut=2 WHERE id=:solicitare ";
        try {
            if (eh.getId()==null) {
                //insertRecord(eh);
            }else{
                SQLQuery query_1 = session.getCurrentSession().createSQLQuery(sql_1);
                query_1.setParameter("id", eh.getId());
                query_1.setParameter("user_executor", eh.getUserExecutor());
                query_1.executeUpdate();
            }
            SQLQuery query_2 = session.getCurrentSession().createSQLQuery(sql_2);
            query_2.setParameter("solicitare", eh.getIdSolicitare());
            query_2.executeUpdate();
            return 1;
        } catch (Exception e) {
            Logger.getLogger(ExecutorHistoryImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
}
