package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Car;
import kg.asiamotors.demo.models.Person;
import kg.asiamotors.demo.repasitories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    public List<Car> findByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }
    public List<Car> findByPersonId (int personId) {
        return carRepository.findByPersonId(personId);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }
    public Car findOne(int id) {
        Optional<Car> foundCar = carRepository.findById(id);
        return foundCar.orElse(null);
    }
    @Transactional
    public void save(Car car) {
        carRepository.save(car);
    }
    @Transactional
    public void update(int id, Car updatedCar) {
        if (carRepository.existsById(id)) {
            updatedCar.setId(id);
            carRepository.save(updatedCar);
        }
    }
    @Transactional
    public void delete(int id) {
        carRepository.deleteById(id);
    }
}



