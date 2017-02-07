package md.cnam.helpdesk.dao;

import java.util.List;
import java.util.Map;
import md.cnam.helpdesk.entity.ClCategorii;
import md.cnam.helpdesk.entity.ClDiviziuni;
import md.cnam.helpdesk.entity.ClUseri;
import md.cnam.helpdesk.model.UserSession;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClUseriImpl extends GenericDaoHibImpl<ClUseri>{
    
    @Transactional
    public Map findByIdLogin(long idLogin){
        //String sql="select * from cl_useri where id_loginCnam=:idLogin";
        String sql = "SELECT u.id,u.idnp,u.nume,u.prenume,u.id_diviziune, di.nume diviziune_nume,u.username,u.id_loginCnam,u.atasat,u.priority, group_concat(cat.id separator ',') id_categoria, group_concat(cat.nume separator ',') id_categoria_nume"
                + " FROM cl_useri u "
                + " left join atrib_user_rol ar "
                + " on u.id=ar.id_user "
                + " left join cl_categorii cat "
                + " on ar.id_rol=cat.id "
                + " LEFT JOIN cl_diviziuni di " 
                + " ON u.id_diviziune=di.idDiviziune "
                + " where u.id_loginCnam=IFNULL(:idLogin,u.id_loginCnam) "
                + " group by u.id "
                + " LIMIT 0,1000";
        
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("idLogin", idLogin);
        //query.addEntity(ClUseri.class);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (Map)query.uniqueResult();
    }
    
    @Transactional
    public Map findByLoginAndPassword(String username, String password){
        String sql = "SELECT u.id,u.idnp,u.nume,u.prenume,u.id_diviziune, di.nume diviziune_nume,u.username,u.id_loginCnam,u.atasat,u.priority, group_concat(cat.id separator ',') id_categoria, group_concat(cat.nume separator ',') id_categoria_nume"
                + " FROM cl_useri u "
                + " left join atrib_user_rol ar "
                + " on u.id=ar.id_user "
                + " left join cl_categorii cat "
                + " on ar.id_rol=cat.id "
                + " LEFT JOIN cl_diviziuni di " 
                + " ON u.id_diviziune=di.idDiviziune "
                + " where u.username=IFNULL(:username,u.username) and u.password=:password"
                + " group by u.id "
                + " LIMIT 0,1000";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("username", username);
        query.setParameter("password", password);
        //query.addEntity(ClUseri.class);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (Map)query.uniqueResult();
    }
    
    /*
    @Transactional
    public Map getUserByIdLogin(long idLogin) {
        //String sql="select u.id,u.idnp,u.nume as nume,u.prenume,u.id_diviziune,d.nume as diviziune,u.id_categoria,u.username,u.id_loginCnam,u.atasat from cl_useri as u,cl_diviziuni as d where u.id_diviziune=d.idDiviziune and u.id_loginCnam=:idLogin";
        String sql = "select u.id,u.idnp,u.nume as nume,u.prenume,u.id_categoria,u.username,u.id_loginCnam,u.atasat,"
                + " (select cl_diviziuni.idDiviziune from cl_diviziuni WHERE idDiviziune=u.id_diviziune) as id_diviziune,"
                + " (select cl_diviziuni.nume from cl_diviziuni WHERE idDiviziune=u.id_diviziune) as diviziune"
                + " from cl_useri as u"
                + " where  u.id_loginCnam=:idLogin";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("idLogin", idLogin);
        //query.addEntity(UserSession.class);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (Map) query.uniqueResult();
    }
    */
    @Transactional
    public List listByCriterya(Map criterya){
        String sidx=(String)criterya.get("sidx");//transmite:sidx: id_diviziune asc,
        if (sidx.contains(" ")) {
            criterya.put("sord", "");
            sidx = sidx.replace(",", "");
        }
        
        String sql = "SELECT u.id,u.idnp,u.nume,u.prenume,u.id_diviziune,u.username,u.id_loginCnam,u.atasat,u.priority, group_concat(cat.nume separator ',') id_categoria"
                + " FROM cl_useri u "
                + " left join atrib_user_rol ar "
                + " on u.id=ar.id_user "
                + " left join cl_categorii cat "
                + " on ar.id_rol=cat.id "
                + " where u.id=IFNULL(:id,u.id) "
                + " and u.nume like IFNULL(:nume,u.nume) "
//                + " AND idnp like IFNULL(:idnp,idnp) "
                + " and u.username like IFNULL(:username,u.username) "
                + " group by u.id "
                + " order by "+sidx+" "+criterya.get("sord")+" LIMIT 0,1000";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
        
        Map search=(Map)criterya.get("search");
        String[] searchFields={"id","nume","username"};
        for (String param : searchFields) {
            if (search.containsKey(param)) {
                query.setParameter(param, '%'+(String)search.get(param)+'%');
            }else{
                query.setParameter(param, null);
            }
        }
        
        return query.list();
    }
    
    @Transactional
    public List listNeatasatiByCriterya(Map criterya){
        String sidx=(String)criterya.get("sidx");//transmite:sidx: id_diviziune asc,
        if (sidx.contains(" ")) {
            criterya.put("sord", "");
            sidx = sidx.replace(",", "");
        }
        String sql = "SELECT * FROM ("
                + "SELECT u.id,u.idnp,u.nume,u.prenume,u.id_diviziune,u.username,u.id_loginCnam,u.atasat,u.priority, group_concat(cat.nume separator ',') id_categoria"
                + " FROM cl_useri u "
                + " left join atrib_user_rol ar "
                + " on u.id=ar.id_user "
                + " left join cl_categorii cat "
                + " on ar.id_rol=cat.id "
                + " where u.id=IFNULL(:id,u.id) "
                + " and u.nume like IFNULL(:nume,u.nume) "
//                + " AND idnp like IFNULL(:idnp,idnp) "
                + " and u.username like IFNULL(:username,u.username) "
                + " group by u.id "
                + " order by "+sidx+" "+criterya.get("sord")+" LIMIT 0,1000 "
                + " ) AS it "
                + " WHERE (it.id_diviziune is null OR it.id_categoria is null)";
        
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
        
        Map search=(Map)criterya.get("search");
        String[] searchFields={"id","nume","username"};
        for (String param : searchFields) {
            if (search.containsKey(param)) {
                query.setParameter(param, '%'+(String)search.get(param)+'%');
            }else{
                query.setParameter(param, null);
            }
        }
        
        return query.list();
    }
    
    @Transactional
    public int customUpdate(ClUseri user){
        String sql="UPDATE cl_useri SET "
                + "idnp=IFNULL(:idnp,idnp),"
                + "nume=IFNULL(:nume,nume),"
                + "username=IFNULL(:username,username),"
                + "id_diviziune=IFNULL(:id_diviziune,id_diviziune),"
                + "atasat=IFNULL(:atasat,atasat)"
                + " WHERE id=:id ";//OR id_loginCnam=IFNULL(:id_loginCnam,id_loginCnam)
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", user.getId());
        //System.out.println("user.getId():"+user.getId());
        //query.setParameter("id_loginCnam", user.getIdloginCnam());
        query.setParameter("idnp", user.getIdnp());
        query.setParameter("nume", user.getNume());
        query.setParameter("username", user.getUsername());
        if (user.getIdDiviziune()==null) {
            query.setParameter("id_diviziune", null);
        } else {
            if (user.getIdDiviziune().getIdDiviziune()==0) {
                query.setParameter("id_diviziune", null);
            }else query.setParameter("id_diviziune", user.getIdDiviziune().getIdDiviziune());
        }
        query.setParameter("atasat", user.getAtasat());
        //query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
        //System.out.println(user.getId());
        //System.out.println(user.getIdCategoria());
        //System.out.println(user.getIdDiviziune());
        //System.out.println(user.getNume());
        
        
        return query.executeUpdate();
    }
    
    @Transactional
    public List listDSI(){
        String sql="select u.id,u.nume from cl_useri as u, atrib_user_rol as aur WHERE u.id = aur.id_user and aur.id_rol=3 OR aur.id_rol=28 OR aur.id_rol=29 ";
        SQLQuery query = session.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
}
