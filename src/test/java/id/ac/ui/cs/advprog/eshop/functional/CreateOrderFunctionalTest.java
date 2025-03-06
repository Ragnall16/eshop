package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
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

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateOrderFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private ProductRepository productRepository;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);

        Iterator<Product> existingProducts = productRepository.findAll();
        if (!existingProducts.hasNext()) {
            Product product = new Product();
            product.setProductName("Test Product");
            product.setProductQuantity(10);
            productRepository.create(product);
        }
    }

    @Test
    void userCanCreateOrder(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");

        WebElement authorInput = driver.findElement(By.id("authorInput"));
        WebElement productSelect = driver.findElement(By.id("productsSelect"));
        WebElement submitButton = driver.findElement(By.tagName("button"));

        authorInput.sendKeys("The GOAT Cristiano Ronaldo");

        WebElement firstProduct = productSelect.findElement(By.tagName("option"));
        firstProduct.click();

        submitButton.click();

        driver.get(baseUrl + "/order/history?author=The GOAT Cristiano Ronaldo");
        String pageSource = driver.getPageSource();

        assertTrue(pageSource.contains("The GOAT Cristiano Ronaldo"));
        assertTrue(pageSource.contains("WAITING_PAYMENT"));
    }
}
