package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface Findable<T> {
    List<T> findAll();
    T findById(String id);
}
