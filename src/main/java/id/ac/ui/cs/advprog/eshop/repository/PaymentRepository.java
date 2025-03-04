package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {

    private List<Payment> paymentData = new ArrayList<>();
    private Map<String, Order> paymentOrder = new HashMap<>();

    public Payment save(Order order, Payment payment) {
        paymentData.add(payment);
        paymentOrder.put(payment.getId(), order);
        if (payment.getStatus().equals("SUCCESS")) {
            order.setStatus("SUCCESS");
        } else if (payment.getStatus().equals("REJECTED")) {
            order.setStatus("FAILED");
        } else {
            throw new IllegalArgumentException();
        }

        return payment;
    }

    public void update(Payment payment, String status) {
        Order order = this.getOrder(payment.getId());
        payment.setStatus(status);
        if (status.equals("SUCCESS")) {
            order.setStatus("SUCCESS");
        } else if (status.equals("REJECTED")) {
            order.setStatus("FAILED");
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Payment findById(String paymentId) {
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(paymentId)) {
                return savedPayment;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        List<Payment> allPayments = new ArrayList<>();
        for (Payment payment : paymentData) {
            allPayments.add(payment);
        }

        return allPayments;
    }

    public Order getOrder(String paymentId) {
        return paymentOrder.get(paymentId);
    }
}