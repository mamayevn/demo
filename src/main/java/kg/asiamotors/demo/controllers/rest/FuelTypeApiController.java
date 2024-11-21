package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.services.FuelTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuelTypes")
public class FuelTypeApiController {
    private final FuelTypeService fuelTypeService;

    public FuelTypeApiController(FuelTypeService fuelTypeService) {
        this.fuelTypeService = fuelTypeService;
    }

    @GetMapping
    public List<FuelTypeDTO> getAllFuelTypes() {
        return fuelTypeService.getAllFuelTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelTypeDTO> getFuelTypeById(@PathVariable int id) {
        return fuelTypeService.getFuelTypeById(id);
    }

    @PostMapping
    public ResponseEntity<FuelTypeDTO> createFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeService.createFuelType(fuelTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelTypeDTO> updateFuelType(@PathVariable int id, @RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeService.updateFuelType(id, fuelTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelType(@PathVariable int id) {
        return fuelTypeService.deleteFuelType(id);
    }
}
