/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpSession;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.entity.TiketOpenHistory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TiketOpenHistoryDao extends GenericDaoHibImpl<TiketOpenHistory>{
    @Autowired
    private HttpSession httpSession;
    
    @Transactional
    public void saveOrUpdate(int idTiket) {
        ClUseri sessionUser = (ClUseri) httpSession.getAttribute("user");
        TiketOpenHistory tOpenHist = new TiketOpenHistory();
        tOpenHist.setIdTiket(idTiket);
        tOpenHist.setIdUser(sessionUser.getId());
        tOpenHist.setDateOpened(new Date());

        Integer tOpenHistId=getTiketOpenHistory(idTiket, sessionUser.getId());
        if (tOpenHistId == null) {
            save(tOpenHist);
        } else {
            tOpenHist.setId(tOpenHistId);
            update(tOpenHist);
        }
    }
    
    @Transactional
    public Integer getTiketOpenHistory(int idTiket, int idUser){
        String sql ="select id from tiket_open_history where id_user=:id_user and id_tiket=:id_tiket";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_tiket",idTiket);
        query.setParameter("id_user",idUser);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        Map resoult=(Map)query.uniqueResult();
        if (resoult!=null) {
            return (Integer)resoult.get("id");
        }
        return null;
    }
}
