package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    OrderService orderService;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData){
        return null;
    }

    @Override
    public Payment setStatus(Payment payment, String status){
        return null;
    }

    @Override
    public Payment getPayment(String paymentId){
        return null;
    }

    @Override
    public List<Payment> getAllPayments(){
        return null;
    }
}