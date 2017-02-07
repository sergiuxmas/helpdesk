/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.entity.Executare;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ExecutareImpl extends GenericDaoHibImpl<Executare>{
    
    @Transactional
    public List listSortDesc(Integer idTiket){
        String sql="SELECT ex.descriere as descriere,u.nume as nume,DATE_FORMAT(ex.data_executarii,'%d.%m.%Y %H:%i:%s') as data_executarii FROM executare as ex,cl_useri as u WHERE ex.id_executor=u.id AND ex.id_solicitare=:id_tiket ORDER BY ex.data_executarii DESC";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_tiket", idTiket);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    @Transactional
    public Map findByIdTiket(int idTiket){
        String sql=""+
            "SELECT ex.id," +
            "      ex.id_prioritate," +
            "      (select p.nume from cl_prioritate as p where p.id=ex.id_prioritate) as prioritate," +
            "      ex.tip_solicitare," +
            "      (select tip.nume from cl_solicitare_tip as tip where tip.id=ex.tip_solicitare) as tip," +
            "      ex.id_domeniu," +
            "      (select d.nume from cl_domeniu as d where d.id=ex.id_domeniu) as domeniu," +
            "      ex.id_executor," +
            "      (select u.nume from cl_useri as u where u.id=ex.id_executor) as user," +
            "      ex.descriere," +
            "      DATE_FORMAT(ex.data_executarii,'%d.%m.%Y') as data_executarii " +
            "FROM " +
            "     executare as ex " +
            "WHERE " +
            "    ex.id_solicitare=:id_solicitare";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_solicitare", idTiket);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (Map)query.uniqueResult();
    }
    
    @Transactional
    public void saveOrUpdate(Executare ex){
        String sqlUpdate="UPDATE executare SET id_prioritate=IFNULL(:id_prioritate,id_prioritate),tip_solicitare=IFNULL(:tip_solicitare,tip_solicitare),data_executarii=NOW(),descriere=:descriere,id_domeniu=IFNULL(:id_domeniu,id_domeniu) WHERE id_solicitare=:id_solicitare";
        String sqlInsert="INSERT INTO executare (id_prioritate,tip_solicitare,data_executarii,descriere,id_domeniu,id_solicitare,id_executor) VALUES (IFNULL(:id_prioritate,id_prioritate),IFNULL(:tip_solicitare,tip_solicitare),NOW(),:descriere,IFNULL(:id_domeniu,id_domeniu),:id_solicitare,:id_executor)";
  
        SQLQuery queryUpdate = session.getCurrentSession().createSQLQuery(sqlUpdate);
        queryUpdate.setParameter("id_prioritate", ex.getIdPrioritate());
        queryUpdate.setParameter("tip_solicitare", ex.getTipSolicitare());
        queryUpdate.setParameter("id_domeniu", ex.getIdDomeniu());
        queryUpdate.setParameter("descriere", ex.getDescriere());
        queryUpdate.setParameter("id_domeniu", ex.getIdDomeniu());
        queryUpdate.setParameter("id_solicitare", ex.getIdSolicitare());
        int rezUpdate=queryUpdate.executeUpdate();
        if (rezUpdate==0) {
            SQLQuery queryInsert = session.getCurrentSession().createSQLQuery(sqlInsert);
            queryInsert.setParameter("id_prioritate", ex.getIdPrioritate());
            queryInsert.setParameter("tip_solicitare", ex.getTipSolicitare());
            queryInsert.setParameter("id_domeniu", ex.getIdDomeniu());
            queryInsert.setParameter("descriere", ex.getDescriere());
            queryInsert.setParameter("id_domeniu", ex.getIdDomeniu());
            queryInsert.setParameter("id_solicitare", ex.getIdSolicitare());
            queryInsert.setParameter("id_executor", ex.getIdExecutor());
            queryInsert.executeUpdate();
        }
    }
}
