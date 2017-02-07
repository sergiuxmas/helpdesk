package md.cnam.helpdesk.service;
import java.util.List;

public interface GenericServiceIntf<T> {
     void save(T t);
     T findById(long id);
     void update(T t);
     void delete(T t);
     List<T> list();
    
}
