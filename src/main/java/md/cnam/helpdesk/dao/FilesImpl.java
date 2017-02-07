/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.List;
import md.cnam.helpdesk.entity.Files;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FilesImpl extends GenericDaoHibImpl<Files>{
    @Transactional
    public List getFilesByIdTiket(int idTiket){
        String sql="select * from files where id_tiket=:id_tiket order by date_upload asc";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_tiket", idTiket);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        return query.list();
    }
}
