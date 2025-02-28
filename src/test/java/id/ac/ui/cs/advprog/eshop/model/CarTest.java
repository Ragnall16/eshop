package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    @Test
    public void testCarGettersAndSetters() {
        Car car = new Car();

        car.setCarId("123e4567-e89b-12d3-a456-426614174000");
        car.setCarName("Royce");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        assertEquals("123e4567-e89b-12d3-a456-426614174000", car.getCarId());
        assertEquals("Royce", car.getCarName());
        assertEquals("Red", car.getCarColor());
        assertEquals(5, car.getCarQuantity());
    }
}