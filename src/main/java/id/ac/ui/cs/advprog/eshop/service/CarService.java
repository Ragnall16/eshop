package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.List;

public interface CarService extends Findable<Car>, Modifiable<Car>{
    public Car create(Car car);
    public List<Car> findAll();
    Car findById(String carId);
    public Car update(Car car);
    public void deleteById(String carId);
}