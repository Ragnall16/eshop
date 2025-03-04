package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp(){
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePayment(){
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER", paymentData);
        assertEquals("79c3179f-e220-458e-9224-146466dec4ff", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "WOMP",
                    paymentData);
        });
    }

    @Test
    void testCreatePaymentInvalidPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER", null);
        });
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER", paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER", paymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("WOMP WOMP");
        });
    }

    @Test
    void testValidVoucherCode() {
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER",
                paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testInvalidSubFeature() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("notVoucher", "ISHOP1234ABC5678");
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNotSixteenCharacters() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "1");
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNotStartWithESHOP() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ISHOP1234ABC5678");
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNotContainsEightNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeIsNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", "VOUCHER",
                paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}