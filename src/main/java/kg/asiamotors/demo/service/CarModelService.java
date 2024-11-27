package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.CarModelDTO;
import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.repository.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarModelService {

    private final CarModelRepository carModelRepository;
    private final BrandService brandService;
    private final VolumeService volumeApiService;
    private final TransmissionService transmissionService;
    private final DriveService driveService;
    private final FuelTypeService fuelTypeService;

    public CarModelService(CarModelRepository carModelRepository,
                           BrandService brandService,
                           VolumeService volumeApiService,
                           TransmissionService transmissionService,
                           DriveService driveService,
                           FuelTypeService fuelTypeService) {
        this.carModelRepository = carModelRepository;
        this.brandService = brandService;
        this.volumeApiService = volumeApiService;
        this.transmissionService = transmissionService;
        this.driveService = driveService;
        this.fuelTypeService = fuelTypeService;
    }

    public List<CarModelDTO> getAllCarModels() {
        return carModelRepository.findAll().stream()
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
    }

    public CarModel createCarModel(CarModelDTO carModelDTO) {
        CarModel carModel = new CarModel();
        carModel.setName(carModelDTO.getName());
        carModel.setBrand(brandService.findById(carModelDTO.getBrandId()));
        carModel.setVolume(volumeApiService.findById(carModelDTO.getVolumeId()));
        carModel.setTransmission(transmissionService.findById(carModelDTO.getTransmissionId()));
        carModel.setDrive(driveService.findEntityById(carModelDTO.getDriveId()));
        carModel.setFuelType(fuelTypeService.findById(carModelDTO.getFuelTypeId()));
        return carModelRepository.save(carModel);
    }

    public CarModel updateCarModel(int id, CarModelDTO carModelDTO) {
        CarModel existingModel = carModelRepository.findById(id).orElse(null);
        if (existingModel != null) {
            existingModel.setName(carModelDTO.getName());
            existingModel.setBrand(brandService.findById(carModelDTO.getBrandId()));
            existingModel.setVolume(volumeApiService.findById(carModelDTO.getVolumeId()));
            existingModel.setTransmission(transmissionService.findById(carModelDTO.getTransmissionId()));
            existingModel.setDrive(driveService.findEntityById(carModelDTO.getDriveId()));
            existingModel.setFuelType(fuelTypeService.findById(carModelDTO.getFuelTypeId()));
            return carModelRepository.save(existingModel);
        }
        return null;
    }

    public boolean deleteCarModel(int id) {
        if (carModelRepository.existsById(id)) {
            carModelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
