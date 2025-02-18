package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Product product = new Product(); // No ID passed since it will be generated
        product.setProductName("Steak");
        product.setProductQuantity(2);

        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        assertEquals(product, result);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        // Arrange
        Product product1 = new Product();
        product1.setProductName("Steak");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductName("Fried Chicken");
        product2.setProductQuantity(9);

        List<Product> productList = Arrays.asList(product1, product2);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productService.findAll();

        assertEquals(productList, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        // Arrange
        Product product = new Product();
        product.setProductName("Ice Crean");
        product.setProductQuantity(3);

        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        assertEquals(product, result);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDelete() {
        // Arrange
        String productId = "1";

        // Act
        productService.delete(productId);

        // Assert
        verify(productRepository, times(1)).delete(productId);
    }
}