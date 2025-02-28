package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Toyota");

        when(carRepository.create(car)).thenReturn(car);

        Car createdCar = carService.create(car);

        assertNotNull(createdCar);
        assertEquals("123", createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarId("123");
        car1.setCarName("Toyota");

        Car car2 = new Car();
        car2.setCarId("456");
        car2.setCarName("Honda");

        Iterator<Car> carIterator = Arrays.asList(car1, car2).iterator();
        when(carRepository.findAll()).thenReturn(carIterator);

        List<Car> cars = carService.findAll();

        assertEquals(2, cars.size());
        assertEquals("Toyota", cars.get(0).getCarName());
        assertEquals("Honda", cars.get(1).getCarName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Toyota");

        when(carRepository.findById("123")).thenReturn(car);

        Car foundCar = carService.findById("123");

        assertNotNull(foundCar);
        assertEquals("123", foundCar.getCarId());
        assertEquals("Toyota", foundCar.getCarName());
        verify(carRepository, times(1)).findById("123");
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Toyota");

        when(carRepository.update(car)).thenReturn(car);

        Car updatedCar = carService.update(car);

        assertNotNull(updatedCar);
        assertEquals("123", updatedCar.getCarId());
        verify(carRepository, times(1)).update(car);
    }

    @Test
    void testDeleteById() {
        doNothing().when(carRepository).delete("123");

        carService.deleteById("123");

        verify(carRepository, times(1)).delete("123");
    }
}