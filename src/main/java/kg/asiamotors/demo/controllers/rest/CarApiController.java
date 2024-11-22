package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.CarDTO;
import kg.asiamotors.demo.services.api.CarApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarApiController {

    private final CarApiService carApiService;

    public CarApiController(CarApiService carApiService) {
        this.carApiService = carApiService;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> cars = carApiService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        CarDTO createdCar = carApiService.createCar(carDTO);
        return ResponseEntity.ok(createdCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable int id, @RequestBody CarDTO carDTO) {
        CarDTO updatedCar = carApiService.updateCar(id, carDTO);
        if (updatedCar == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable int id) {
        if (!carApiService.deleteCar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
