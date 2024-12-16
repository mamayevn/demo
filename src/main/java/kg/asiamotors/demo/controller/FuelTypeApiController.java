package kg.asiamotors.demo.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
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
    private final MeterRegistry meterRegistry;
    private final Counter fuelTypeRequestCounter;
    private final Timer fuelTypeRequestTimer;

    public FuelTypeApiController(FuelTypeService fuelTypeService, MeterRegistry meterRegistry) {
        this.fuelTypeService = fuelTypeService;
        this.meterRegistry = meterRegistry;
        this.fuelTypeRequestCounter = meterRegistry.counter("fuel_type_requests_total");
        this.fuelTypeRequestTimer = meterRegistry.timer("fuel_type_requests_duration");
    }

    @GetMapping
    @Operation(summary = "Получение списка типа топлива")
    public List<FuelTypeDTO> getAllFuelTypes() {
        fuelTypeRequestCounter.increment();
        long start = System.nanoTime();
        List<FuelTypeDTO> fuelTypes = fuelTypeService.getAllFuelTypes();
        fuelTypeRequestTimer.record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
        return fuelTypes;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение списка типа топлива по id")
    public ResponseEntity<FuelTypeDTO> getFuelTypeById(@PathVariable int id) {
        fuelTypeRequestCounter.increment();
        long start = System.nanoTime();
        ResponseEntity<FuelTypeDTO> response = fuelTypeService.getFuelTypeById(id);
        fuelTypeRequestTimer.record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
        return response;
    }

    @PostMapping
    @Operation(summary = "Создать тип топлива")
    public ResponseEntity<FuelTypeDTO> createFuelType(@RequestBody FuelTypeDTO fuelTypeDTO) {
        fuelTypeRequestCounter.increment();
        long start = System.nanoTime();
        ResponseEntity<FuelTypeDTO> response = fuelTypeService.createFuelType(fuelTypeDTO);
        fuelTypeRequestTimer.record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
        return response;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип топлива")
    public ResponseEntity<FuelTypeDTO> updateFuelType(@PathVariable int id, @RequestBody FuelTypeDTO fuelTypeDTO) {
        fuelTypeRequestCounter.increment();
        long start = System.nanoTime();
        ResponseEntity<FuelTypeDTO> response = fuelTypeService.updateFuelType(id, fuelTypeDTO);
        fuelTypeRequestTimer.record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип топлива")
    public ResponseEntity<Void> deleteFuelType(@PathVariable int id) {
        fuelTypeRequestCounter.increment();
        long start = System.nanoTime();
        ResponseEntity<Void> response = fuelTypeService.deleteFuelType(id);
        fuelTypeRequestTimer.record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
        return response;
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск типа топлива по имени")
    public List<FuelTypeDTO> searchFuelTypesByName(@RequestParam String name) {
        fuelTypeRequestCounter.increment();
        long start = System.nanoTime();
        List<FuelTypeDTO> fuelTypes = fuelTypeService.searchFuelTypesByName(name);
        fuelTypeRequestTimer.record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
        return fuelTypes;
    }

    @GetMapping("page")
    @Operation(summary = "Получение списка типа топлива с пагинацией")
    public ResponseEntity<Page<FuelTypeDTO>> getAllFuelTypeDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                               @RequestParam(name = "limit", defaultValue = "10") int limit) {
        fuelTypeRequestCounter.increment();
        long start = System.nanoTime();
        ResponseEntity<Page<FuelTypeDTO>> response = ResponseEntity.ok(fuelTypeService.getAllFuelTypeDto(offset, limit));
        fuelTypeRequestTimer.record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
        return response;
    }

    @GetMapping("/cache")
    @Operation(summary = "Кэширование данных")
    public ResponseEntity<String> cacheFuelTypes() {
        fuelTypeRequestCounter.increment();
        long start = System.nanoTime();
        ResponseEntity<String> response = ResponseEntity.ok("Кэш обновлен!");
        fuelTypeRequestTimer.record(System.nanoTime() - start, java.util.concurrent.TimeUnit.NANOSECONDS);
        return response;
    }
}
