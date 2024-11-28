package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.service.FuelTypeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuelTypes")
@Tag(name = "Fuel Type")
public class FuelTypeApiController {
    private final FuelTypeService fuelTypeService;

    public FuelTypeApiController(FuelTypeService fuelTypeService) {
        this.fuelTypeService = fuelTypeService;
    }

    @GetMapping
    @Operation(summary = "Получение списка типа топлива")
    public List<FuelTypeDTO> getAllFuelTypes() {
        return fuelTypeService.getAllFuelTypes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение списка типа топлива по id")
    public ResponseEntity<FuelTypeDTO> getFuelTypeById(@PathVariable int id) {
        return fuelTypeService.getFuelTypeById(id);
    }

    @PostMapping
    @Operation(summary = "Создать тип топлива")
    public ResponseEntity<FuelTypeDTO> createFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeService.createFuelType(fuelTypeDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип топлива")
    public ResponseEntity<FuelTypeDTO> updateFuelType(@PathVariable int id, @RequestBody FuelTypeDTO fuelTypeDTO) {
        return fuelTypeService.updateFuelType(id, fuelTypeDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип топлива")
    public ResponseEntity<Void> deleteFuelType(@PathVariable int id) {
        return fuelTypeService.deleteFuelType(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск типа топлива по имени")
    public List<FuelTypeDTO> searchFuelTypesByName(@RequestParam String name) {
        return fuelTypeService.searchFuelTypesByName(name);
    }
    @GetMapping("page")
    @Operation(summary = "Получение списка типа топлива с пагинацией")
    public ResponseEntity<Page<FuelTypeDTO>> getAllFuelTypeDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                         @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(fuelTypeService.getAllFuelTypeDto(offset, limit));
    }
}
