package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.CarDTO;
import kg.asiamotors.demo.models.Car;
import kg.asiamotors.demo.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarApiController {

    private final CarService carService;

    public CarApiController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> cars = carService.findAll().stream()
                .map(car -> {
                    CarDTO dto = new CarDTO();
                    dto.setBrand(car.getBrand());
                    dto.setModel(car.getModel());
                    dto.setEngineVolume(car.getEngineVolume());
                    dto.setYear(car.getYear());
                    dto.setPrice(car.getPrice());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody CarDTO carDTO) {
        Car car = new Car();
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setEngineVolume(carDTO.getEngineVolume());
        car.setYear(carDTO.getYear());
        car.setPrice(carDTO.getPrice());
        carService.save(car);
        return ResponseEntity.ok(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable int id, @RequestBody CarDTO carDTO) {
        Car existingCar = carService.findOne(id);
        if (existingCar == null) {
            return ResponseEntity.notFound().build();
        }

        existingCar.setBrand(carDTO.getBrand());
        existingCar.setModel(carDTO.getModel());
        existingCar.setEngineVolume(carDTO.getEngineVolume());
        existingCar.setYear(carDTO.getYear());
        existingCar.setPrice(carDTO.getPrice());
        carService.save(existingCar);
        return ResponseEntity.ok(existingCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable int id) {
        Car existingCar = carService.findOne(id);
        if (existingCar == null) {
            return ResponseEntity.notFound().build();
        }

        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
