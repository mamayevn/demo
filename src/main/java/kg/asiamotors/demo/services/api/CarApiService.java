package kg.asiamotors.demo.services.api;

import kg.asiamotors.demo.dto.CarDTO;
import kg.asiamotors.demo.models.Car;
import kg.asiamotors.demo.repasitories.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarApiService {

    private final CarRepository carRepository;

    public CarApiService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<CarDTO> getAllCarDto(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return carRepository.findAllCarDtos(pageable);
    }

    @Transactional
    public CarDTO createCar(CarDTO carDTO) {
        Car car = convertToEntity(carDTO);
        Car savedCar = carRepository.save(car);
        return convertToDTO(savedCar);
    }

    @Transactional
    public CarDTO updateCar(int id, CarDTO carDTO) {
        return carRepository.findById(id)
                .map(existingCar -> {
                    existingCar.setBrand(carDTO.getBrand());
                    existingCar.setModel(carDTO.getModel());
                    existingCar.setEngineVolume(carDTO.getEngineVolume());
                    existingCar.setYear(carDTO.getYear());
                    existingCar.setPrice(carDTO.getPrice());
                    Car updatedCar = carRepository.save(existingCar);
                    return convertToDTO(updatedCar);
                })
                .orElse(null);
    }

    @Transactional
    public boolean deleteCar(int id) {
        if (!carRepository.existsById(id)) {
            return false;
        }
        carRepository.deleteById(id);
        return true;
    }

    private CarDTO convertToDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setBrand(car.getBrand());
        carDTO.setModel(car.getModel());
        carDTO.setEngineVolume(car.getEngineVolume());
        carDTO.setYear(car.getYear());
        carDTO.setPrice(car.getPrice());
        return carDTO;
    }

    private Car convertToEntity(CarDTO carDTO) {
        Car car = new Car();
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setEngineVolume(carDTO.getEngineVolume());
        car.setYear(carDTO.getYear());
        car.setPrice(carDTO.getPrice());
        return car;
    }
    public List<CarDTO> searchCarsByBrand(String brand) {
        List<Car> cars = carRepository.findByBrand(brand);
        return cars.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}