package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        // This method is intentionally left empty.
        // If necessary, initialize common test dependencies here.
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals(car.getCarName(), savedCar.getCarName());
        assertEquals(car.getCarColor(), savedCar.getCarColor());
        assertEquals(car.getCarQuantity(), savedCar.getCarQuantity());
    }

    @Test
    void testCreateCarWithExistingId() {
        Car car = new Car();
        car.setCarId("existing-id-123");
        car.setCarName("Honda");
        car.setCarColor("Blue");
        car.setCarQuantity(3);

        Car createdCar = carRepository.create(car);

        assertEquals("existing-id-123", createdCar.getCarId());
        assertEquals("Honda", createdCar.getCarName());
        assertEquals("Blue", createdCar.getCarColor());
        assertEquals(3, createdCar.getCarQuantity());
    }


    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar() {
        Car car1 = new Car();
        car1.setCarName("Car1");
        car1.setCarColor("Blue");
        car1.setCarQuantity(2);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarName("Car2");
        car2.setCarColor("Green");
        car2.setCarQuantity(3);
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car1.getCarId(), savedCar.getCarId());
        savedCar = carIterator.next();
        assertEquals(car2.getCarId(), savedCar.getCarId());
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testUpdateCarSuccess() {
        Car car = new Car();
        car.setCarName("CarY");
        car.setCarColor("White");
        car.setCarQuantity(4);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarId(car.getCarId());
        updatedCar.setCarName("UpdatedCar");
        updatedCar.setCarColor("Yellow");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update(updatedCar);
        assertNotNull(result);
        assertEquals("UpdatedCar", result.getCarName());
        assertEquals("Yellow", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateCarFailure() {
        Car car = new Car();
        car.setCarName("CarA");
        car.setCarColor("Gray");
        car.setCarQuantity(6);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarId("non-existent-id");
        updatedCar.setCarName("NonExisting");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(1);

        Car result = carRepository.update(updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteCarSuccess() {
        Car car = new Car();
        car.setCarName("CarZ");
        car.setCarColor("Silver");
        car.setCarQuantity(2);
        car = carRepository.create(car);

        carRepository.delete(car.getCarId());

        assertNull(carRepository.findById(car.getCarId()));
    }

    @Test
    void testDeleteCarFailure() {
        Car car = new Car();
        car.setCarName("CarB");
        car.setCarColor("Blue");
        car.setCarQuantity(3);
        carRepository.create(car);

        carRepository.delete("non-existent-id");

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
    }

    @Test
    public void testFindById() {
        Car car = new Car();
        car.setCarName("Honda");
        car.setCarColor("Black");
        car.setCarQuantity(10);
        car = carRepository.create(car);
        String carId = car.getCarId();

        Car foundCar = carRepository.findById(carId);
        assertNotNull(foundCar);
        assertEquals(carId, foundCar.getCarId());
        assertEquals("Honda", foundCar.getCarName());
        assertEquals("Black", foundCar.getCarColor());
        assertEquals(10, foundCar.getCarQuantity());

        Car notFoundCar = carRepository.findById("non-existent-id");
        assertNull(notFoundCar);
    }
}