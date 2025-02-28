package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService extends Findable<Product>, Modifiable<Product>{
    public Product create(Product product);
    public List<Product> findAll();
    Product findById(String id);
    public Product update(Product product);
    public void deleteById(String productId);
}