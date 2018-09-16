package app.model.dao;

import java.util.List;

public interface EntityDAO<T> {

    boolean insert(T entity);
    boolean delete(T entity);
    boolean update(T oldEntity, T newEntity);
    T find(T entity);
    List<T> getAll();
    boolean contains(T entity);

}
