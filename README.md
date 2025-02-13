# E-shop

[Reflection 1](#Reflection-1) <br>

## Reflection 1

The clean code principles that have been applied to my codes are:
### 1. Single Responsibility Principle (SRP)
    Each class has a single responsibility, ProductController handles HTTP requests and responses,
    ProductService manages logic, and ProductRepository manages data access and storage
### 2. Don't Repeat Yourself (DRY)
    The findAll method in ProductServiceImpl reuses the productRepository.findAll() method
### 3. Meaningful Naming
    Variables, methods, and classes are named descriptively which makes the code self-explanatory
### 4. KISS (Keep It Simple, Stupid)
    The code is straightforward and avoids unnecessary complexity
### 5. Separation of Concerns
    The code is organized into layers (controller, service, repository), which improves modularity and testability
<br>

While the secure coding practices applied are:
### 1. Use of @Autowired
    Dependency injection is used properly with @Autowired, which promotes loose coupling and testability
### 2. Avoiding Direct Data Exposure
    The repository layer encapsulates the productData list, preventing direct access from outside the class
### 3. Use of @PathVariable and @ModelAttribute
    These annotations ensure that data is bound securely to method parameters in the controller
### 4. Input Validation
    The Product model uses @Getter and @Setter from Lombok, which ensures controlled access to fields
<br>

For mistakes in my code, I did not find a glaring mistake that would change the correctness of my code, <br>
but there are areas of my code where improvement could be found useful. <br>
Those areas for example are, validation for the inputted product name and quantity, lack of error handling, etc.