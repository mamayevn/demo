package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.CarModelDTO;
import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.service.CarModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
@Tag(name = "Car Models")
public class CarModelController {

    private final CarModelService carModelService;

    @GetMapping
    @Operation(summary = "Получение всех моделей")
    public ResponseEntity<List<CarModelDTO>> getAllCarModels() {
        List<CarModelDTO> models = carModelService.getAllCarModels();
        return ResponseEntity.ok(models);
    }

    @PostMapping
    @Operation(summary = "Создание модели")
    public ResponseEntity<CarModel> createCarModel(@RequestBody CarModelDTO carModelDTO) {
        CarModel carModel = carModelService.createCarModel(carModelDTO);
        return ResponseEntity.ok(carModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление модели")
    public ResponseEntity<CarModel> updateCarModel(@PathVariable int id, @RequestBody CarModelDTO carModelDTO) {
        CarModel updatedCarModel = carModelService.updateCarModel(id, carModelDTO);
        if (updatedCarModel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCarModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить модель")
    public ResponseEntity<Void> deleteCarModel(@PathVariable int id) {
        boolean isDeleted = carModelService.deleteCarModel(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
