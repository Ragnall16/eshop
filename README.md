# E-shop

---
<strong>

Ragnall Muhammad Al Fath ~ 2306210550 ~ AdPro B 


[Reflection 1](#Reflection-1) <br>
[Reflection 2](#Reflection-2) <br> 

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

