package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

public interface AbstractRepository<T> {
    T create(T entity);
    Iterator<T> findAll();
    T findById(String id);
    T update(T entity);
    void delete(String id);
}