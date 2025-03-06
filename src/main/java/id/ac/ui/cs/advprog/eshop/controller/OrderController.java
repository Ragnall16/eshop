package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        model.addAttribute("order", new Order(UUID.randomUUID().toString(), null, System.currentTimeMillis(), ""));
        model.addAttribute("productList", productService.findAll());
        return "CreateOrder";
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam String author, @RequestParam List<String> products) {
        if (products == null || products.isEmpty()) {
            return "redirect:/order/create?error=emptyProducts";
        }

        List<Product> productList = products.stream()
                .map(productService::findById).toList();

        Order order = new Order(UUID.randomUUID().toString(), productList, System.currentTimeMillis(), author);
        orderService.createOrder(order);

        return "redirect:/order/history?author=" + author;
    }

    @GetMapping("/history")
    public String orderHistoryPage(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "OrderHistory";
    }
}