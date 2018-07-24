package app.model.DAO;

import java.util.List;

public interface EntityDAO<T> {

    boolean insert(T entity);
    boolean delete(T entity);
    boolean update(T entity, T newEntity);
    T find(T entity);
    List<T> getAll();

}
