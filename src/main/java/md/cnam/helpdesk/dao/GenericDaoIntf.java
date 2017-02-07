package md.cnam.helpdesk.dao;

import java.util.List;

public interface GenericDaoIntf<T> {
    void testInt();
    void save(T t);
    T findById(long id);
    void update(T t);
    void delete(T t);
    List<T> list();
}
