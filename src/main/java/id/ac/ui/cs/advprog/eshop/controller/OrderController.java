package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        return null;
    }

    @PostMapping("/create")
    public String createOrderPost(@ModelAttribute Order order, Model model) {
        return null;
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        return null;
    }
}