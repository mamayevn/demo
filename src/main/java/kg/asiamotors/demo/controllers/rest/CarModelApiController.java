package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.CarModelDTO;
import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.services.CarModelService;
import kg.asiamotors.demo.services.BrandService;
import kg.asiamotors.demo.services.VolumeService;
import kg.asiamotors.demo.services.TransmissionService;
import kg.asiamotors.demo.services.DriveService;
import kg.asiamotors.demo.services.FuelTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/models")
public class CarModelApiController {

    private final CarModelService carModelService;
    private final BrandService brandService;
    private final VolumeService volumeService;
    private final TransmissionService transmissionService;
    private final DriveService driveService;
    private final FuelTypeService fuelTypeService;

    public CarModelApiController(CarModelService carModelService,
                                 BrandService brandService,
                                 VolumeService volumeService,
                                 TransmissionService transmissionService,
                                 DriveService driveService,
                                 FuelTypeService fuelTypeService) {
        this.carModelService = carModelService;
        this.brandService = brandService;
        this.volumeService = volumeService;
        this.transmissionService = transmissionService;
        this.driveService = driveService;
        this.fuelTypeService = fuelTypeService;
    }

    // Получение всех моделей
    @GetMapping
    public ResponseEntity<List<CarModelDTO>> getAllCarModels() {
        List<CarModelDTO> models = carModelService.getAllCarModels();
        return ResponseEntity.ok(models);
    }

    @PostMapping
    public ResponseEntity<CarModel> createCarModel(@RequestBody CarModelDTO carModelDTO) {
        CarModel carModel = carModelService.createCarModel(carModelDTO);
        return ResponseEntity.ok(carModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarModel> updateCarModel(@PathVariable int id, @RequestBody CarModelDTO carModelDTO) {
        CarModel updatedCarModel = carModelService.updateCarModel(id, carModelDTO);
        if (updatedCarModel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCarModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarModel(@PathVariable int id) {
        boolean isDeleted = carModelService.deleteCarModel(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
