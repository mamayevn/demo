package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.services.api.FuelTypeApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuelTypes")
public class FuelTypeApiController {
    private final FuelTypeApiService fuelTypeApiService;

    public FuelTypeApiController(FuelTypeApiService fuelTypeApiService) {
        this.fuelTypeApiService = fuelTypeApiService;
    }

    @GetMapping
    public List<FuelTypeDTO> getAllFuelTypes() {
        return fuelTypeApiService.getAllFuelTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelTypeDTO> getFuelTypeById(@PathVariable int id) {
        return fuelTypeApiService.getFuelTypeById(id);
    }

    @PostMapping
    public ResponseEntity<FuelTypeDTO> createFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeApiService.createFuelType(fuelTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelTypeDTO> updateFuelType(@PathVariable int id, @RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeApiService.updateFuelType(id, fuelTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelType(@PathVariable int id) {
        return fuelTypeApiService.deleteFuelType(id);
    }
}
