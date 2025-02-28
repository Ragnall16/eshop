package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface AbstractService<T> {
    T create(T entity);
    List<T> findAll();
    T findById(String id);
    T update(T entity);
    void deleteById(String id);
}
