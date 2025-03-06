package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private List<Payment> payments;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        payment1 = new Payment("afd3f1f8-f058-43cc-9a75-16d0026366a6", "VOUCHER", paymentData1);
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "ESHOP1234ABC5679");
        payment2 = new Payment("ed10f995-e93e-4194-932e-dd7b2c39ba9a", "VOUCHER", paymentData2);
        payments.add(payment2);
    }

    @Test
    void testGetPaymentDetail() throws Exception {
        when(paymentService.getPayment(payment1.getId())).thenReturn(payment1);

        mockMvc.perform(get("/payment/detail/" + payment1.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testListPayments() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentList"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testGetAdminPaymentDetail() throws Exception {
        when(paymentService.getPayment(payment1.getId())).thenReturn(payment1);

        mockMvc.perform(get("/payment/admin/detail/" + payment1.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("AdminPaymentDetail"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attributeExists("allowedStatuses"));
    }

    @Test
    void testSetPaymentStatusToSuccess() throws Exception {
        when(paymentService.getPayment(payment1.getId())).thenReturn(payment1);

        mockMvc.perform(post("/payment/admin/set-status/" + payment1.getId())
                        .param("status", PaymentStatus.SUCCESS.getValue()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/detail/" + payment1.getId()));

        verify(paymentService, times(1)).setStatus(payment1, PaymentStatus.SUCCESS.getValue());
    }

    @Test
    void testSetPaymentStatusToRejected() throws Exception {
        when(paymentService.getPayment(payment2.getId())).thenReturn(payment2);

        mockMvc.perform(post("/payment/admin/set-status/" + payment2.getId())
                        .param("status", PaymentStatus.REJECTED.getValue()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/detail/" + payment2.getId()));

        verify(paymentService, times(1)).setStatus(payment2, PaymentStatus.REJECTED.getValue());
    }
}