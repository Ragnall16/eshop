package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private PaymentService paymentService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setProductId(UUID.randomUUID().toString());
        product1.setProductName("Laptop");

        product2 = new Product();
        product2.setProductId(UUID.randomUUID().toString());
        product2.setProductName("Smartphone");
    }

    @Test
    void testCreateOrderPage() throws Exception {
        when(productService.findAll()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateOrder"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("productList"));
    }

    @Test
    void testCreateOrderPost() throws Exception {
        when(productService.findById(anyString())).thenReturn(product1);

        mockMvc.perform(post("/order/create")
                        .param("author", "JohnDoe")
                        .param("products", product1.getProductId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history?author=JohnDoe"));

        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    void testOrderHistoryPage() throws Exception {
        when(orderService.findAllByAuthor("JohnDoe")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/order/history").param("author", "JohnDoe"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderHistory"))
                .andExpect(model().attributeExists("orders"));
    }
}