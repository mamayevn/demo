package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.service.FuelTypeService;
import org.springframework.data.domain.Page;
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
    @GetMapping("/search")
    public List<FuelTypeDTO> searchFuelTypesByName(@RequestParam String name) {
        return fuelTypeService.searchFuelTypesByName(name);
    }
    @GetMapping("page")
    public ResponseEntity<Page<FuelTypeDTO>> getAllFuelTypeDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                         @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(fuelTypeService.getAllFuelTypeDto(offset, limit));
    }
}
