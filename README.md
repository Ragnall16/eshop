# E-shop

---
<strong>

Ragnall Muhammad Al Fath ~ 2306210550 ~ AdPro B 

---

## Website URL
[E-shop](https://exclusive-leoline-ragnall-a4263600.koyeb.app/)

---

## Reflections
[Reflection 1](#Reflection-1) <br>
[Reflection 2](#Reflection-2) <br>
[Reflection 3](#Reflection-3) (Module 2 Reflection) <br>
[Reflection 4](#Reflection-4) (Module 3 Reflection) <br>

</strong>

---

## Reflection 1

<b> The clean code principles that have been applied to my codes are: </b>

#### 1. Single Responsibility Principle (SRP)
Each class has a single responsibility, ProductController handles HTTP requests and responses,
ProductService manages logic, and ProductRepository manages data access and storage

#### 2. Don't Repeat Yourself (DRY)
The findAll method in ProductServiceImpl reuses the productRepository.findAll() method

#### 3. Meaningful Naming
Variables, methods, and classes are named descriptively which makes the code self-explanatory

#### 4. KISS (Keep It Simple, Stupid)
The code is straightforward and avoids unnecessary complexity

#### 5. Separation of Concerns
The code is organized into layers (controller, service, repository), which improves modularity and testability
<br><br>

**While the secure coding practices applied are:**

#### 1. Use of @Autowired
Dependency injection is used properly with @Autowired, which promotes loose coupling and testability

#### 2. Avoiding Direct Data Exposure
The repository layer encapsulates the productData list, preventing direct access from outside the class

#### 3. Use of @PathVariable and @ModelAttribute
These annotations ensure that data is bound securely to method parameters in the controller

#### 4. Input Validation
The Product model uses @Getter and @Setter from Lombok, which ensures controlled access to fields
<br> <br>

**For mistakes in my code, I did not find a glaring mistake that would change the correctness of my code, <br>
but there are areas of my code where improvement could be found useful.** 
**Those areas for example are, validation for the inputted product name and quantity, lack of error handling, etc.**

---

## Reflection 2

**After writing the unit test, I feel like my code is correct and stable (good for expected/unexpected scenarios) and each method is working as intended.**  

**On the topic of how many unit tests should be made in a class, I don't think there is a fixed number of unit tests per class, it depends on the class itself. What I look for in writing tests are test the methods that are public
ensure all logical paths are covered, and cover edge case + failure scenarios**  

**A code coverage of over 80% makes sure that our unit tests are enough to verify our program. <br>
But, 100% code coverage does not mean my code has no bugs or errors, it just means every line of code is tested. <br>
There may be edge cases not tested, logical errors, etc.**  

**If I create a new functional test suite that verifies the number of items in the product list with the same setup procedures and instance variables, 
then there would be some potential code issues, which are:**

#### 1. Code Duplication 
If every functional test suite has the same setup procedures and instance variables, 
then this violates the Don't Repeat Yourself (DRY) Principle

#### 2. Lack of Abstraction
If the test logic is not abstracted into reusable methods, the test code can become verbose and hard to read.

#### 3. Reduced Maintainability
If the test data is hardcoded in multiple places, it becomes harder to maintain.
<br>

**The improvements we can make on the code to fix these issues and make the code cleaner are:**  <br> 
1. Create a base test class to centralize setup logic that contains the common setup code.
2. Avoid hardcoded values and use constants
3. Keep each test to verify one specific behaviour
4. Now that we have a base test class, we need to modify the original CreateProductFunctionalTest.java to extend the base class

---

## Reflection 3

### Code Quality Issues from SonarCloud that I fixed:

### 1. Group dependencies by their destination 

Previous Code
```Java
// build.gradle.kts

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
    testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
    testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}
```
**To fix this issue, I grouped together testImplementation and testRuntimeOnly**

Fixed Code
```Java
// build.gradle.kts

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
    testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
    testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
```

### 2. Use constructor injection instead of field injection (2x)

Previous Code
```Java
// ProductServiceImpl.java

@Autowired
private ProductRepository productRepository;

// ProductController.java

@Autowired
private ProductService service;
```

**To fix this issue, I changed the Autowired injection from field injection to an injection on the class constructor**

Fixed Code
```Java
// ProductServiceImpl.java

private final ProductRepository productRepository;

@Autowired
public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
}

// ProductController.java

private final ProductService service;

@Autowired
public ProductController(ProductService service) {
    this.service = service;
}
```

### 3. Add a nested comment explaining why this method is empty

Previous Code
```Java
// EshopApplicationTests.java

@Test
void contextLoads() {
}

// ProductRepositoryTest.java

@BeforeEach
void setUp() {
}
```

**To fix this issue, is pretty self-explanatory, I just put a comment inside the method explaining why that method is empty**

Fixed Code
```Java
// EshopApplicationTests.java

@Test
void contextLoads() {
    // No implementation needed – verifies context startup
}

// ProductRepositoryTest.java

@BeforeEach
void setUp() {
    // This method is intentionally left empty.
    // If necessary, initialize common test dependencies here.
}
```

### 4. Remove the declaration of thrown exception 'java.lang.Exception'

Previous Code
```Java
// HomePageFunctionalTest.java

@Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception{
    ...
}

@Test
void welcomeMessage_homePage_isCorrect(ChromeDriver driver) throws Exception{
    ...
}
```

**To fix this issue, also pretty self-explanatory, I removed "throws Exception"**

Fixed Code
```Java
// HomePageFunctionalTest.java

@Test
    void pageTitle_isCorrect(ChromeDriver driver) {
    ...
}

@Test
void welcomeMessage_homePage_isCorrect(ChromeDriver driver) {
    ...
}
```

### 5. Remove useless assignment to local variable 'product'

Previous Code
```Java
// ProductRepositoryTest.java

void testDeleteProductFailure() {
    Product product = new Product();
    product.setProductName("Samsung S23 Ultra");
    product.setProductQuantity(1);
    product = productRepository.create(product);

    productRepository.delete("e3274332-7c8c-4d38-b3e7-1b2f3e9fd431"); // Non Existent ID

    Iterator<Product> productIterator = productRepository.findAll();
    assertTrue(productIterator.hasNext());
}
```

**To fix this issue, I just put productRepository.create(product); instead of assigning it to a variable since it is not needed**

Fixed Code
```Java
// ProductRepositoryTest.java

@Test
void testDeleteProductFailure() {
    Product product = new Product();
    product.setProductName("Samsung S23 Ultra");
    product.setProductQuantity(1);
    productRepository.create(product);

    productRepository.delete("e3274332-7c8c-4d38-b3e7-1b2f3e9fd431"); // Non Existent ID

    Iterator<Product> productIterator = productRepository.findAll();
    assertTrue(productIterator.hasNext());
}
```


### CI/CD 
What are the requirements an app needs to achieve to be able to say it has met 
the definition of Continuous Integration and Continuous Deployment, 
I think there are three main ones:
1. Continuous Integration

    - ci.yml runs unit tests when there is a push / pull request. 
    - scorecard.yml checks the repository's supply chain security
    - sonarcloud.yml performs static code analysis to detect bugs, vulnerabilities, and maintainability issues
    - The workflow ensures the app is consistently tested in a standardized environment (Java 21 on Ubuntu)

2. Continuous Deployment

    - Autodeploy to Koyeb when the main branch is updated (push / pull request to main branch)

3. Stable and Reliable in Delivery

    - If a test fails, the deployment is stopped so a broken code will not go to production
    - Scorecard and SonarCloud ensures Security and Code Quality
    - Every commit triggers the same automated steps, eliminating human errors and inconsistencies


---

## Reflection 4

### 1. The Principles I applied to this project

1. Single Responsibility Principle (SRP): Separated CarController from ProductController to ensure each class has only one responsibility.

2. Open/Closed Principle (OCP): Introduced abstract classes for Service and Repository so new functionalities can be added without modifying existing code.
   
3. Liskov Substitution Principle (LSP): Removed redundant service interfaces, ensuring that subclasses can replace their base classes without breaking functionality.
   
4. Interface Segregation Principle (ISP): Replaced AbstractService<T> with Findable<T> and Modifiable<T> so classes only depend on the methods they actually use.
   
5. Dependency Inversion Principle (DIP): Re-added CarService and ProductService to prevent controllers from depending on concrete implementations, ensuring dependency on abstractions instead.

### 2. Advantage of Applying SOLID principles to my project

1. SRP Example: By separating CarController from ProductController, we reduce complexity and make each controller easier to manage, modify, and test.

2. OCP Example: With abstract service and repository classes, we can add new services (e.g., BikeService) without modifying existing services.
   
3. LSP Example: Removing redundant interfaces ensures that substituting a CarService with a VehicleService won’t break existing functionality.
   
4. ISP Example: By splitting AbstractService<T>, CarService now only implements methods relevant to cars rather than being forced to implement unrelated methods.
   
5. DIP Example: Controllers now depend on interfaces rather than concrete service implementations, making the system more flexible and easier to test with mock dependencies.


### 3. Disadvantages of Not Applying SOLID Principles to my project

1. SRP: If CarController and ProductController were combined, a change in one part (e.g., adding a new car feature) might unintentionally affect products.
   
2. OCP: If services didn’t use abstract classes, every time a new feature was added, existing classes might require modifications, increasing the risk of introducing bugs.
   
3. LSP: If a subclass (CarService) introduced behavior that its parent (VehicleService) didn’t expect, substituting one for another could lead to runtime errors.
   
4. ISP: A generic AbstractService<T> forces implementations to include unnecessary methods, making classes bulkier and violating encapsulation.
   
5. DIP: If controllers directly depended on concrete implementations, changing the database or adding caching layers would require modifying the controllers, leading to tight coupling and less flexibility.


---