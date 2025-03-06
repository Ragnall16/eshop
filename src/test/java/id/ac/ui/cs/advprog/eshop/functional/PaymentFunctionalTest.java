package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class PaymentFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    private Payment testPayment;
    private String testPaymentId;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);

        List<Order> existingOrders = orderService.findAllByAuthor("Test Author");
        Order testOrder;

        if (!existingOrders.isEmpty()) {
            testOrder = existingOrders.get(0);
        } else {
            testOrder = new Order("test-order-id", new ArrayList<>(), System.currentTimeMillis(), "Test Author", "PENDING");
            orderService.createOrder(testOrder);
        }

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Test Bank");
        paymentData.put("referenceCode", "1234567890");

        testPayment = new Payment("test-payment-id", PaymentMethod.BANK_TRANSFER.getValue(), paymentData);
        testPaymentId = testPayment.getId();
        paymentRepository.save(testOrder, testPayment);
    }

    @Test
    void userCanViewPaymentForm(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Payment Form"));
    }

    @Test
    void userCanViewPaymentDetails(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail/" + testPaymentId);

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Test Bank"));
        assertTrue(pageSource.contains("1234567890"));
    }

    @Test
    void adminCanViewAllPayments(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/list");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Test Bank"));
        assertTrue(pageSource.contains("1234567890"));
    }

    @Test
    void adminCanViewPaymentDetails(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/" + testPaymentId);

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Test Bank"));
        assertTrue(pageSource.contains("1234567890"));

        WebElement acceptButton = driver.findElement(By.id("acceptButton"));
        WebElement rejectButton = driver.findElement(By.id("rejectButton"));

        assertTrue(acceptButton.isDisplayed());
        assertTrue(rejectButton.isDisplayed());
    }

    @Test
    void adminCanSetPaymentStatus(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/" + testPaymentId);

        WebElement acceptButton = driver.findElement(By.id("acceptButton"));
        acceptButton.click();

        driver.get(baseUrl + "/payment/detail/" + testPaymentId);
        String pageSource = driver.getPageSource();

        assertTrue(pageSource.contains(PaymentStatus.SUCCESS.getValue()));
    }
}