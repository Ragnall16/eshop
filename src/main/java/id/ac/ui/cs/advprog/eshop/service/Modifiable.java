package id.ac.ui.cs.advprog.eshop.service;

public interface Modifiable<T> {
    T create(T entity);
    T update(T entity);
    void deleteById(String id);
}
