package md.cnam.helpdesk.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import md.cnam.helpdesk.entity.GenericEntityIntf;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Component//("diviziuniDao")
public abstract class GenericDaoHibImpl<T extends Serializable> /*implements GenericDaoIntf<T>*/ extends GenericDaoAbstract<T>{

    private static final Logger LOG = Logger.getLogger(GenericDaoHibImpl.class.getName());
    /*
     @PersistenceContext
     private EntityManager entityManager;
     */
    @Autowired
    protected SessionFactory session;

    private Class<T> type;
    /*
    public GenericDaoHibImpl() {
        
    }
    */
    @SuppressWarnings("unchecked")
     public GenericDaoHibImpl() {
        Type genericSuperClass = getClass().getGenericSuperclass();
        
        ParameterizedType parametrizedType = null;
        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }
        type = (Class<T>) parametrizedType.getActualTypeArguments()[0];
     }
     
     /*
    @SuppressWarnings("unchecked")
    public GenericDaoHibImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }
    */
     

    protected Class<T> getType() {
        return this.type;
    }

    protected String getClassName() {
        return type.getName();
    }
    
    @Transactional
    @Override
    public T findById(long id) {
        return (T) session.getCurrentSession().get(type, (int)id);
        //
        // return this.entityManager.find(type, id);
        //
    }

    @Transactional
    @Override
    public List<T> list() {
        return session.getCurrentSession().createQuery("from " + getClassName()).list();
        //
        // return (List<T>) entityManager.createQuery("select t from "+type.getName()+" t")
        //.getResultList();
        //
    }

    @Transactional
    @Override
    public void save(T t) {
        session.getCurrentSession().save(t);
        //
        // entityManager.persist(t);
        //
    }
    
    @Transactional
    public int saveAndGetId(T t) {
        int id = -1;
        //Transaction trans=session.getCurrentSession().beginTransaction();
        try{
            Serializable ser = session.getCurrentSession().save(t);   
            if (ser != null) {
                id = (Integer) ser;
            }
            //trans.commit();     
        }
        catch(HibernateException he){}
        return id;
    }

    @Transactional
    @Override
    public void update(T t) {
        session.getCurrentSession().update(t);
        //
        // return this.entityManager.merge(t);
        //
    }

    @Transactional
    @Override
    public void delete(T t) {
        session.getCurrentSession().delete(t);
        //
        // t = this.entityManager.merge(t);
        // this.entityManager.remove(t);
        //
        LOG.info("T=" + t.toString());
    }
}
