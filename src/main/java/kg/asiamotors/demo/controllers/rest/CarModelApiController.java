package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.CarModelDTO;
import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.services.api.CarModelApiService;
import kg.asiamotors.demo.services.api.BrandApiService;
import kg.asiamotors.demo.services.api.VolumeApiService;
import kg.asiamotors.demo.services.api.TransmissionApiService;
import kg.asiamotors.demo.services.api.DriveApiService;
import kg.asiamotors.demo.services.api.FuelTypeApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class CarModelApiController {

    private final CarModelApiService carModelApiService;
    private final BrandApiService brandApiService;
    private final VolumeApiService volumeApiService;
    private final TransmissionApiService transmissionApiService;
    private final DriveApiService driveApiService;
    private final FuelTypeApiService fuelTypeApiService;

    public CarModelApiController(CarModelApiService carModelApiService,
                                 BrandApiService brandApiService,
                                 VolumeApiService volumeApiService,
                                 TransmissionApiService transmissionApiService,
                                 DriveApiService driveApiService,
                                 FuelTypeApiService fuelTypeApiService) {
        this.carModelApiService = carModelApiService;
        this.brandApiService = brandApiService;
        this.volumeApiService = volumeApiService;
        this.transmissionApiService = transmissionApiService;
        this.driveApiService = driveApiService;
        this.fuelTypeApiService = fuelTypeApiService;
    }

    // Получение всех моделей
    @GetMapping
    public ResponseEntity<List<CarModelDTO>> getAllCarModels() {
        List<CarModelDTO> models = carModelApiService.getAllCarModels();
        return ResponseEntity.ok(models);
    }

    @PostMapping
    public ResponseEntity<CarModel> createCarModel(@RequestBody CarModelDTO carModelDTO) {
        CarModel carModel = carModelApiService.createCarModel(carModelDTO);
        return ResponseEntity.ok(carModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarModel> updateCarModel(@PathVariable int id, @RequestBody CarModelDTO carModelDTO) {
        CarModel updatedCarModel = carModelApiService.updateCarModel(id, carModelDTO);
        if (updatedCarModel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCarModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarModel(@PathVariable int id) {
        boolean isDeleted = carModelApiService.deleteCarModel(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
