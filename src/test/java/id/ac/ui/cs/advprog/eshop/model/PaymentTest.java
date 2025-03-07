package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp(){
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePayment(){
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals("79c3179f-e220-458e-9224-146466dec4ff", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("79c3179f-e220-458e-9224-146466dec4ff", "WOMP", paymentData);
        });
    }

    @Test
    void testCreatePaymentInvalidPaymentData() {
        assertThrows(IllegalArgumentException.class, () ->
                new Payment("79c3179f-e220-458e-9224-146466dec4ff", PaymentMethod.VOUCHER.getValue(), null)
        );
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", PaymentMethod.VOUCHER.getValue(), paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", PaymentMethod.VOUCHER.getValue(), paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("WOMP WOMP"));
    }

    @Test
    void testValidVoucherCode() {
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @ParameterizedTest(name = "{index} => key={0}, voucherCode={1}")
    @MethodSource("invalidVoucherProvider")
    void testInvalidVoucher(String key, String voucherCode) {
        Map<String, String> localPaymentData = new HashMap<>();
        localPaymentData.put(key, voucherCode);
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff",
                PaymentMethod.VOUCHER.getValue(),
                localPaymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    private static Stream<Arguments> invalidVoucherProvider() {
        return Stream.of(
                // Test case where key is not "voucherCode"
                Arguments.of("notVoucher", "ISHOP1234ABC5678"),
                // Voucher code is not 16 characters long
                Arguments.of("voucherCode", "1"),
                // Voucher code does not start with "ESHOP"
                Arguments.of("voucherCode", "ISHOP1234ABC5678"),
                // Voucher code does not contain eight numbers
                Arguments.of("voucherCode", "ESHOPABCDEFGHIJK"),
                // Voucher code is null
                Arguments.of("voucherCode", null)
        );
    }

    @Test
    void testValidBankTransfer() {
        Map<String, String> localPaymentData = new HashMap<>();
        localPaymentData.put("bankName", "BCA");
        localPaymentData.put("referenceCode", "FREE");
        Payment payment = new Payment("79c3179f-e220-458e-9224-146466dec4ff", PaymentMethod.BANK_TRANSFER.getValue(), localPaymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testBankTransferEmptyNameOrRefCode() {
        Map<String, String> paymentDataEmptyCode = new HashMap<>();
        paymentDataEmptyCode.put("bankName", "BCA");
        paymentDataEmptyCode.put("referenceCode", "");
        Payment paymentEmptyCode = new Payment("79c3179f-e220-458e-9224-146466dec4ff", PaymentMethod.BANK_TRANSFER.getValue(), paymentDataEmptyCode);

        Map<String, String> paymentDataEmptyName = new HashMap<>();
        paymentDataEmptyName.put("bankName", "");
        paymentDataEmptyName.put("referenceCode", "FREE");
        Payment paymentEmptyName = new Payment("296227be-b3c4-421a-9990-8d74bd1a023a", PaymentMethod.BANK_TRANSFER.getValue(), paymentDataEmptyName);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentEmptyCode.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), paymentEmptyName.getStatus());
    }

    @Test
    void testBankTransferNullNameOrRefCode() {
        Map<String, String> paymentDataNullCode = new HashMap<>();
        paymentDataNullCode.put("bankName", "BCA");
        paymentDataNullCode.put("referenceCode", null);
        Payment paymentNullCode = new Payment("79c3179f-e220-458e-9224-146466dec4ff", PaymentMethod.BANK_TRANSFER.getValue(), paymentDataNullCode);

        Map<String, String> paymentDataNullName = new HashMap<>();
        paymentDataNullName.put("bankName", null);
        paymentDataNullName.put("referenceCode", "FREE");
        Payment paymentNullName = new Payment("296227be-b3c4-421a-9990-8d74bd1a023a", PaymentMethod.BANK_TRANSFER.getValue(), paymentDataNullName);

        assertEquals(PaymentStatus.REJECTED.getValue(), paymentNullCode.getStatus());
        assertEquals(PaymentStatus.REJECTED.getValue(), paymentNullName.getStatus());
    }

    @Test
    void testBankTransferInvalidData() {
        Map<String, String> localPaymentData = new HashMap<>();
        localPaymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("296227be-b3c4-421a-9990-8d74bd1a023a", PaymentMethod.BANK_TRANSFER.getValue(), localPaymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}
