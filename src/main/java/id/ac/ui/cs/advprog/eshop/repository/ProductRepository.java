package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product update(Product product) {
        for (Product p : productData) {
            if (p.getProductId().equals(product.getProductId())) {
                p.setProductName(product.getProductName());
                p.setProductQuantity(product.getProductQuantity());
                return p;
            }
        }
        return null;
    }

    public void delete(String productId) {
        productData.removeIf(p -> p.getProductId().equals(productId));
    }
}