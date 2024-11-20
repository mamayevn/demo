package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.CarModelDTO;
import kg.asiamotors.demo.models.*;
import kg.asiamotors.demo.services.*;
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
        List<CarModelDTO> models = carModelService.findAll().stream()
                .map(carModel -> {
                    CarModelDTO dto = new CarModelDTO();
                    dto.setName(carModel.getName());
                    dto.setBrandId(carModel.getBrand().getId());
                    dto.setVolumeId(carModel.getVolume().getId());
                    dto.setTransmissionId(carModel.getTransmission().getId());
                    dto.setDriveId(carModel.getDrive().getId());
                    dto.setFuelTypeId(carModel.getFuelType().getId());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(models);
    }

    @PostMapping
    public ResponseEntity<CarModel> createCarModel(@RequestBody CarModelDTO carModelDTO) {
        CarModel carModel = new CarModel();
        carModel.setName(carModelDTO.getName());
        carModel.setBrand(brandService.findById(carModelDTO.getBrandId()));
        carModel.setVolume(volumeService.findById(carModelDTO.getVolumeId()));
        carModel.setTransmission(transmissionService.findById(carModelDTO.getTransmissionId()));
        carModel.setDrive(driveService.findById(carModelDTO.getDriveId()));
        carModel.setFuelType(fuelTypeService.findById(carModelDTO.getFuelTypeId()));
        carModelService.save(carModel);
        return ResponseEntity.ok(carModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarModel> updateCarModel(@PathVariable int id, @RequestBody CarModelDTO carModelDTO) {
        CarModel existingModel = carModelService.findById(id);
        if (existingModel == null) {
            return ResponseEntity.notFound().build();
        }

        existingModel.setName(carModelDTO.getName());
        existingModel.setBrand(brandService.findById(carModelDTO.getBrandId()));
        existingModel.setVolume(volumeService.findById(carModelDTO.getVolumeId()));
        existingModel.setTransmission(transmissionService.findById(carModelDTO.getTransmissionId()));
        existingModel.setDrive(driveService.findById(carModelDTO.getDriveId()));
        existingModel.setFuelType(fuelTypeService.findById(carModelDTO.getFuelTypeId()));
        carModelService.save(existingModel);
        return ResponseEntity.ok(existingModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarModel(@PathVariable int id) {
        CarModel existingModel = carModelService.findById(id);
        if (existingModel == null) {
            return ResponseEntity.notFound().build();
        }

        carModelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
