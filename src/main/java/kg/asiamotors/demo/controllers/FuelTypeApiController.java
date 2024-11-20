package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.models.FuelType;
import kg.asiamotors.demo.services.FuelTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fuelTypes")
public class FuelTypeApiController {
    private final FuelTypeService fuelTypeService;

    public FuelTypeApiController(FuelTypeService fuelTypeService) {
        this.fuelTypeService = fuelTypeService;
    }

    @GetMapping
    public List<FuelTypeDTO> getAllFuelTypes() {
        return fuelTypeService.findAll()
                .stream()
                .map(fuelType -> new FuelTypeDTO(fuelType.getId(), fuelType.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelTypeDTO> getFuelTypeById(@PathVariable int id) {
        FuelType fuelType = fuelTypeService.findById(id);
        if (fuelType != null) {
            return ResponseEntity.ok(new FuelTypeDTO(fuelType.getId(), fuelType.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FuelTypeDTO> createFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        FuelType fuelType = new FuelType();
        fuelType.setName(fuelTypeDTO.getName());
        fuelTypeService.save(fuelType);
        return ResponseEntity.ok(new FuelTypeDTO(fuelType.getId(), fuelType.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelTypeDTO> updateFuelType(@PathVariable int id, @RequestBody FuelTypeDTO fuelTypeDTO) {
        FuelType existingFuelType = fuelTypeService.findById(id);
        if (existingFuelType != null) {
            existingFuelType.setName(fuelTypeDTO.getName());
            fuelTypeService.save(existingFuelType);
            return ResponseEntity.ok(new FuelTypeDTO(existingFuelType.getId(), existingFuelType.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelType(@PathVariable int id) {
        FuelType existingFuelType = fuelTypeService.findById(id);
        if (existingFuelType != null) {
            fuelTypeService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
