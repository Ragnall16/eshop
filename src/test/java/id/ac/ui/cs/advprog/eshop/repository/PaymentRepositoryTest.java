package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;

    List<Payment> payments;

    Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        payments = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("f583dcd4-1407-4acf-b109-dbf1191f4b19");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("abb00ecc-5453-4162-b4c5-53ac13364aff",
                products, 1708560000L, "Me");

        Map<String, String > paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("afd3f1f8-f058-43cc-9a75-16d0026366a6", "VOUCHER", paymentData1);
        payments.add(payment1);
        Map<String, String > paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "ESHOP1234ABC5679");
        Payment payment2 = new Payment("ed10f995-e93e-4194-932e-dd7b2c39ba9a", "VOUCHER", paymentData2);
        payments.add(payment2);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(order, payment);

        Payment findResult = paymentRepository.findById(payments.get(0).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testUpdateStatus() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(order, payment);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", order.getStatus());

        paymentRepository.update(payment, "REJECTED");
        Payment findResult = paymentRepository.findById(payments.get(0).getId());
        Order findOrder = paymentRepository.getOrder(findResult.getId());
        assertEquals("REJECTED", findResult.getStatus());
        assertEquals("FAILED", findOrder.getStatus());
    }

    @Test
    void testUpdateInvalidStatus() {
        Payment payment = payments.get(0);
        paymentRepository.save(order, payment);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.update(payment, "WOMP");
        });
    }

    @Test
    void testFindByIdIfFound() {
        for (Payment payment : payments) {
            paymentRepository.save(order, payment);
        }

        Payment findPayment = paymentRepository.findById(payments.get(0).getId());
        assertEquals(payments.get(0).getId(), findPayment.getId());
        assertEquals(payments.get(0).getMethod(), findPayment.getMethod());
        assertEquals(payments.get(0).getStatus(), findPayment.getStatus());
        assertSame(payments.get(0).getPaymentData(), findPayment.getPaymentData());
    }

    @Test
    void testFindByIdIfNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(order, payment);
        }

        Payment findPayment = paymentRepository.findById("womp");
        assertNull(findPayment);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments) {
            paymentRepository.save(order, payment);
        }

        List<Payment> findPayments = paymentRepository.findAll();
        assertEquals(2, findPayments.size());
    }

    @Test
    void testFindOrderIfFound() {
        Payment payment = payments.get(0);
        paymentRepository.save(order, payment);

        Payment findResult = paymentRepository.findById(payments.get(0).getId());
        Order findOrder = paymentRepository.getOrder(findResult.getId());
        assertEquals(order.getId(), findOrder.getId());
        assertEquals(order.getStatus(), findOrder.getStatus());
        assertEquals(order.getAuthor(), findOrder.getAuthor());
        assertEquals(order.getOrderTime(), findOrder.getOrderTime());
        assertEquals(order.getProducts(), findOrder.getProducts());
    }

    @Test
    void testFindOrderIfNotFound() {
        Order findOrder = paymentRepository.getOrder("womp");
        assertNull(findOrder);
    }
}