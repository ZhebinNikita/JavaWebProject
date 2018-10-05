package by.epam.project.dao;

import by.epam.project.exception.ProjectException;

import java.util.List;

public interface EntityDao<T> {

    boolean insert(T entity) throws ProjectException;
    boolean delete(T entity) throws ProjectException;
    boolean update(T oldEntity, T newEntity) throws ProjectException;
    List<T> takeAll() throws ProjectException;
    boolean contains(T entity) throws ProjectException;

}
