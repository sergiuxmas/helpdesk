/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package md.cnam.helpdesk.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpSession;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.entity.ExecutorHistory;
import md.cnam.helpdesk.entity.Solicitare;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Repository
public class TiketDao extends GenericDaoHibImpl<Solicitare>{
    @Autowired
    private HttpSession httpSession;
    
    @Transactional
    public List listForAdmin(Map criterya){//synchronized
        String sql ="select * from (SELECT" +
                "    s.id as id," +
                "    statut.nume as statut," +
                "    s.statut as id_statut," +
                "    tip.nume as tip," +
                "    (select ex.id_prioritate from executare as ex where ex.id_solicitare=s.id) as ex_prioritate,"+
                "    (select case "+
                "                when ex_prioritate is not null OR ex_prioritate=0 OR ex_prioritate<>s.id_prioritate"+
                "                then ex_prioritate"+
                "                else s.id_prioritate"+
                "            end"+
                "    ) as sol_id_prioritate,"+
                "    (select cl_prioritate.nume from cl_prioritate where cl_prioritate.id=sol_id_prioritate) as prioritate,"+
                "    (select ex.id_domeniu from executare as ex where ex.id_solicitare=s.id) as ex_domeniu," +
                "    (select case " +
                "                when ex_domeniu is not null OR ex_domeniu=0 OR ex_domeniu<>s.id_domeniu" +
                "                then ex_domeniu" +
                "                else s.id_domeniu" +
                "            end" +
                "    ) as sol_id_domeniu,"+
                "    (select cl_domeniu.nume from cl_domeniu where cl_domeniu.id=sol_id_domeniu) as domeniu," +
                "    s.data_solicitarii as data_solicitarii," +
                "    s.id_user as id_user," +
                "    cl_useri.nume as user," +
                "    cl_useri.id_diviziune as id_diviziune," +
                "    di.nume as diviziune_nume,"+
                "    (" +
                "      select di.nume from cl_diviziuni as di where di.idDiviziune=id_diviziune" +
                "    ) as diviziune," +
                "    (" +
                "      select h.user_executor from solicitare as sol, executor_history as h" +
                "      where sol.id=h.id_solicitare" +
                "        and sol.id = s.id" +
                "      order by h.data_decizie desc LIMIT 1" +
                "    ) as user_executor," +
                "    (" +
                "      select u.nume from cl_useri as u where u.id=user_executor" +
                "    )as user_executor_name," +
                "    LEFT(s.descriere,120) as descriere," +
                "    s.opened as opened" +
                "    FROM solicitare AS s" +
                "    LEFT JOIN cl_solicitare_statut AS statut on statut.id=s.statut " +
               // "    LEFT JOIN cl_domeniu AS d on s.id_domeniu=d.id " +
               // "    LEFT JOIN cl_prioritate AS p on s.id_prioritate=p.id" +
                "    LEFT JOIN cl_useri on s.id_user=cl_useri.id" +
                "    LEFT JOIN cl_diviziuni as di on cl_useri.id_diviziune=di.idDiviziune" +
                "    LEFT JOIN cl_solicitare_tip as tip on s.tip=tip.id " +
                " WHERE "+
               // " d.id=IFNULL(:domeniu,d.id) " +
                " DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
                " AND DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')>=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii_from, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
                " AND DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')<=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii_to, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
               // " AND p.id=IFNULL(:prioritate,p.id) " +
                " AND di.idDiviziune=IFNULL(:diviziune,di.idDiviziune) " +
                " AND cl_useri.nume like IFNULL(:user,cl_useri.nume) " +
                " AND s.descriere like IFNULL(:descriere,s.descriere) "+
                " order by "+criterya.get("sidx")+" "+criterya.get("sord")+" LIMIT 0,1000"+
                ") as lista_solicitarilor "+
                " WHERE sol_id_prioritate=IFNULL(:prioritate,sol_id_prioritate) "+
                 " AND sol_id_domeniu=IFNULL(:domeniu,sol_id_domeniu) "+
                 " AND (user_executor=IFNULL(:user_executor,user_executor) OR user_executor is null)";
        
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        //query.addEntity(Solicitare.class);
        
        Map search=(Map)criterya.get("search");
        String[] searchFilter={"data_solicitarii","data_solicitarii_from","data_solicitarii_to","domeniu","prioritate","diviziune","user","descriere","user_executor"};
        Vector<String> likeFilter=new Vector<String>();
        likeFilter.add("user");
        likeFilter.add("descriere");
        
        for (String param : searchFilter) {
            if (search.containsKey(param)) {
                if (likeFilter.contains(param)) {
                    //System.out.println("contine");
                    query.setParameter(param, '%'+(String)search.get(param)+'%');
                } else query.setParameter(param, (String)search.get(param));
            }else{
                query.setParameter(param, null);
            }
        }
        
        return query.list();
    }
    
    @Transactional
    public List listForUserDSI(Map criterya){     
        String sql ="select * from (SELECT" +
                "    s.id as id," +
                "    statut.nume as statut," +
                "    s.statut as id_statut," +
                "    tip.nume as tip," +
                "    (select ex.id_prioritate from executare as ex where ex.id_solicitare=s.id) as ex_prioritate,"+
                "    (select case "+
                "                when ex_prioritate is not null OR ex_prioritate=0 OR ex_prioritate<>s.id_prioritate"+
                "                then ex_prioritate"+
                "                else s.id_prioritate"+
                "            end"+
                "    ) as sol_id_prioritate,"+
                "    (select cl_prioritate.nume from cl_prioritate where cl_prioritate.id=sol_id_prioritate) as prioritate,"+
                "    (select ex.id_domeniu from executare as ex where ex.id_solicitare=s.id) as ex_domeniu," +
                "    (select case " +
                "                when ex_domeniu is not null OR ex_domeniu=0 OR ex_domeniu<>s.id_domeniu" +
                "                then ex_domeniu" +
                "                else s.id_domeniu" +
                "            end" +
                "    ) as sol_id_domeniu,"+
                "    (select cl_domeniu.nume from cl_domeniu where cl_domeniu.id=sol_id_domeniu) as domeniu," +
                "    s.data_solicitarii as data_solicitarii," +
                "    s.id_user as id_user," +
                "    cl_useri.nume as user," +
                "    cl_useri.id_diviziune as id_diviziune," +
                "    di.nume as diviziune_nume,"+
                "    (" +
                "      select di.nume from cl_diviziuni as di where di.idDiviziune=id_diviziune" +
                "    ) as diviziune," +
                "    (" +
                "      select h.user_executor from solicitare as sol, executor_history as h" +
                "      where sol.id=h.id_solicitare" +
                "        and sol.id = s.id" +
                "      order by h.data_decizie desc LIMIT 1" +
                "    ) as user_executor," +
                "    (" +
                "      select u.nume from cl_useri as u where u.id=user_executor" +
                "    )as user_executor_name," +
                "    LEFT(s.descriere,120) as descriere," +
                "    s.opened as opened" +
                "    FROM solicitare AS s" +
                "    LEFT JOIN cl_solicitare_statut AS statut on statut.id=s.statut " +
               // "    LEFT JOIN cl_domeniu AS d on s.id_domeniu=d.id " +
               // "    LEFT JOIN cl_prioritate AS p on s.id_prioritate=p.id" +
                "    LEFT JOIN cl_useri on s.id_user=cl_useri.id" +
                "    LEFT JOIN cl_diviziuni as di on cl_useri.id_diviziune=di.idDiviziune" +
                "    LEFT JOIN cl_solicitare_tip as tip on s.tip=tip.id " +
                " WHERE "+
               // " d.id=IFNULL(:domeniu,d.id) " +
                " DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
                " AND DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')>=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii_from, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
                " AND DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')<=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii_to, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
                " AND statut.id=IFNULL(:statut,statut.id) " +
               // " AND p.id=IFNULL(:prioritate,p.id) " +
                " AND di.idDiviziune=IFNULL(:diviziune,di.idDiviziune) " +
                " AND cl_useri.nume like IFNULL(:user,cl_useri.nume) " +
                " AND s.descriere like IFNULL(:descriere,s.descriere) "+
                " order by "+criterya.get("sidx")+" "+criterya.get("sord")+" LIMIT 0,1000"+
                ") as lista_solicitarilor "+
                " WHERE sol_id_prioritate=IFNULL(:prioritate,sol_id_prioritate) "+
                " AND sol_id_domeniu=IFNULL(:domeniu,sol_id_domeniu) "+
                " AND (user_executor=IFNULL(:user_executor,user_executor) OR user_executor is null)";
        
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        Map search=(Map)criterya.get("search");
        query.setParameter("user_executor", search.get("user_executor"));
         
        String[] searchFilter={"data_solicitarii","data_solicitarii_from","data_solicitarii_to","statut","domeniu","prioritate","diviziune","user","descriere","user_executor"};
        Vector<String> likeFilter=new Vector<String>();
        likeFilter.add("user");
        likeFilter.add("descriere");
        
        for (String param : searchFilter) {
            if (search.containsKey(param)) {
                if (likeFilter.contains(param)) {
                    //System.out.println("contine");
                    query.setParameter(param, '%'+(String)search.get(param)+'%');
                } else query.setParameter(param, String.valueOf(search.get(param)));
            }else{
                query.setParameter(param, null);
            }
        }
        //System.out.println("!!!!!!!dao:"+search.get("user_executor"));
        return query.list();
    }
    
    @Transactional
    public List listForUser(Map criterya){     
        String sql ="select * from (SELECT" +
                "    s.id as id," +
                "    statut.nume as statut," +
                "    s.statut as id_statut," +
                "    tip.nume as tip," +
                "    (select ex.id_prioritate from executare as ex where ex.id_solicitare=s.id) as ex_prioritate,"+
                "    (select case "+
                "                when ex_prioritate is not null OR ex_prioritate=0 OR ex_prioritate<>s.id_prioritate"+
                "                then ex_prioritate"+
                "                else s.id_prioritate"+
                "            end"+
                "    ) as sol_id_prioritate,"+
                "    (select cl_prioritate.nume from cl_prioritate where cl_prioritate.id=sol_id_prioritate) as prioritate,"+
                "    (select ex.id_domeniu from executare as ex where ex.id_solicitare=s.id) as ex_domeniu," +
                "    (select case " +
                "                when ex_domeniu is not null OR ex_domeniu=0 OR ex_domeniu<>s.id_domeniu" +
                "                then ex_domeniu" +
                "                else s.id_domeniu" +
                "            end" +
                "    ) as sol_id_domeniu,"+
                "    (select cl_domeniu.nume from cl_domeniu where cl_domeniu.id=sol_id_domeniu) as domeniu," +
                "    s.data_solicitarii as data_solicitarii," +
                "    s.id_user as id_user," +
                "    cl_useri.nume as user," +
                "    cl_useri.id_diviziune as id_diviziune," +
                "    di.nume as diviziune_nume,"+
                "    (" +
                "      select di.nume from cl_diviziuni as di where di.idDiviziune=id_diviziune" +
                "    ) as diviziune," +
                "    (" +
                "      select h.user_executor from solicitare as sol, executor_history as h" +
                "      where sol.id=h.id_solicitare" +
                "        and sol.id = s.id" +
                "      order by h.data_decizie desc LIMIT 1" +
                "    ) as user_executor," +
                "    (" +
                "      select u.nume from cl_useri as u where u.id=user_executor" +
                "    )as user_executor_name," +
                "    LEFT(s.descriere,120) as descriere," +
                "    s.opened as opened" +
                "    FROM solicitare AS s" +
                "    LEFT JOIN cl_solicitare_statut AS statut on statut.id=s.statut " +
               // "    LEFT JOIN cl_domeniu AS d on s.id_domeniu=d.id " +
                "    LEFT JOIN cl_useri on s.id_user=cl_useri.id" +
                "    LEFT JOIN cl_diviziuni as di on cl_useri.id_diviziune=di.idDiviziune" +
                "    LEFT JOIN cl_solicitare_tip as tip on s.tip=tip.id " +
                " WHERE "+
               // " d.id=IFNULL(:domeniu,d.id) " +
                " DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
                " AND DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')>=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii_from, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
                " AND DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')<=IFNULL(DATE_FORMAT(STR_TO_DATE(:data_solicitarii_to, '%d.%m.%Y'),'%d.%m.%Y'),DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y')) " +
                " AND statut.id=IFNULL(:statut,statut.id) " +
                " AND di.idDiviziune=IFNULL(:diviziune,di.idDiviziune) " +
                " AND cl_useri.nume like IFNULL(:user,cl_useri.nume) " +
                " AND s.descriere like IFNULL(:descriere,s.descriere) "+
                " AND cl_useri.id=:id_sessionUser "+
                " order by "+criterya.get("sidx")+" "+criterya.get("sord")+" LIMIT 0,1000"+
                ") "+
                " as lista_solicitarilor "+
                " WHERE sol_id_prioritate=IFNULL(:prioritate,sol_id_prioritate) "+
                 " AND sol_id_domeniu=IFNULL(:domeniu,sol_id_domeniu) ";
        
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        Map search=(Map)criterya.get("search");
        //query.setParameter("user_executor", search.get("user_executor"));
        
        String[] params={"data_solicitarii","data_solicitarii_from","data_solicitarii_to","statut","domeniu","prioritate","diviziune","user","id_sessionUser","descriere"};
        //String[] searchFilter={"data_solicitarii","data_solicitarii_from","data_solicitarii_to","domeniu","prioritate","diviziune","user","descriere","user_executor"};
        Vector<String> likeFilter=new Vector<String>();
        likeFilter.add("user");
        likeFilter.add("descriere");
        
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
        //System.out.println("!!!!!!!dao:"+search.get("user_executor"));
        return query.list();
    }

    @Transactional
    public Map getTiket(int id){
        String sql = "SELECT s.id as id,"
                + "d.nume as domeniu,"
                + "s.id_domeniu as id_domeniu,"
                + "s.descriere as descriere,"
                + "s.id_prioritate,"
                + "tip.nume as tip,"
                + "s.tip as id_tip,"
                + "s.statut as statut,"
                + "st.nume as statutNume,"
                + "p.nume as prioritate,"
                + "u.nume as user,"
                + "DATE_FORMAT(s.data_solicitarii,'%d.%m.%Y %H:%i:%s') as data_solicitarii,"
                + "dv.idDiviziune as id_diviziune,"
                + "dv.nume as diviziune, "
                + "    (" 
                + "      select h.id from solicitare as sol, executor_history as h" 
                + "      where sol.id=h.id_solicitare" 
                + "        and sol.id = s.id" 
                + "      order by h.data_decizie desc LIMIT 1" 
                + "    ) as id_executor_history,"
                + "    ("
                + "      select acceptat from executor_history where executor_history.id=id_executor_history"
                + "    ) as acceptat," 
                + "    ( select user_executor from executor_history where id=id_executor_history " 
                + "    ) as user_executor,"
                + "    (" 
                + "      select u.nume from cl_useri as u where u.id=user_executor" 
                + "    )as user_executor_name " 
                //+ "(select nume from cl_useri where id=s.user_decizie) as user_decizie_nume,"
                //+ "DATE_FORMAT(s.data_stabilirii_deciziei,'%d.%m.%Y %H:%i:%s') as data_stabilirii_deciziei "
                + "FROM solicitare AS s,cl_domeniu AS d,cl_prioritate AS p,cl_useri AS u,cl_diviziuni as dv, cl_solicitare_statut as st, cl_solicitare_tip as tip "
                + "WHERE s.id_domeniu=d.id AND s.id_prioritate=p.id AND s.id_user=u.id AND dv.idDiviziune=u.id_diviziune AND st.id=s.statut AND tip.id=s.tip "
                + " AND s.id=:id_tiket ";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id_tiket",id);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
        return (Map)query.uniqueResult();
    }
    
    @Transactional
    public int updateOpenedTiket(Solicitare sol){
        //System.out.println(tiket);
        
        String sql="UPDATE solicitare SET "
                + "opened=IFNULL(:opened,opened)"
                + " WHERE id=:id";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", sol.getId());
        query.setParameter("opened", true);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.executeUpdate();
    }
    
    @Transactional
    public int updateStatusTiket(Solicitare sol){
        String sql="UPDATE solicitare SET "
                + " statut=:statut,"
                + " data_inchiderii_solicitarii=NOW() "
                + " WHERE id=:id";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", sol.getId());
        query.setParameter("statut", sol.getStatut());
        //query.setParameter("user_executor", sol.getUserExecutor());
        return query.executeUpdate();
    }
    
    @Transactional
    public int editDescriptionTiket(int idTiket, String description){
        String sql="UPDATE solicitare SET "
                + " descriere=:description "
                + " WHERE id=:idTiket";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("idTiket", idTiket);
        query.setParameter("description", description);
        //query.setParameter("user_executor", sol.getUserExecutor());
        return query.executeUpdate();
    }
    
    
}
