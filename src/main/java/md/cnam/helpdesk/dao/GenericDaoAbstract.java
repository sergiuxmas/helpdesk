package md.cnam.helpdesk.dao;

import java.util.List;

public abstract class GenericDaoAbstract<T>{
    public abstract void save(T t);
    public abstract T findById(long id);
    public abstract void update(T t);
    public abstract void delete(T t);
    public abstract List<T> list();
}
