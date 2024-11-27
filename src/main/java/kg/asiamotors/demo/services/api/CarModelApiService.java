package kg.asiamotors.demo.services.api;

import kg.asiamotors.demo.dto.CarModelDTO;
import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.repositories.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarModelApiService {

    private final CarModelRepository carModelRepository;
    private final BrandApiService brandApiService;
    private final VolumeApiService volumeApiService;
    private final TransmissionApiService transmissionApiService;
    private final DriveApiService driveApiService;
    private final FuelTypeApiService fuelTypeApiService;

    public CarModelApiService(CarModelRepository carModelRepository,
                              BrandApiService brandApiService,
                              VolumeApiService volumeApiService,
                              TransmissionApiService transmissionApiService,
                              DriveApiService driveApiService,
                              FuelTypeApiService fuelTypeApiService) {
        this.carModelRepository = carModelRepository;
        this.brandApiService = brandApiService;
        this.volumeApiService = volumeApiService;
        this.transmissionApiService = transmissionApiService;
        this.driveApiService = driveApiService;
        this.fuelTypeApiService = fuelTypeApiService;
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
        carModel.setBrand(brandApiService.findById(carModelDTO.getBrandId()));
        carModel.setVolume(volumeApiService.findById(carModelDTO.getVolumeId()));
        carModel.setTransmission(transmissionApiService.findById(carModelDTO.getTransmissionId()));
        carModel.setDrive(driveApiService.findEntityById(carModelDTO.getDriveId()));
        carModel.setFuelType(fuelTypeApiService.findById(carModelDTO.getFuelTypeId()));
        return carModelRepository.save(carModel);
    }

    public CarModel updateCarModel(int id, CarModelDTO carModelDTO) {
        CarModel existingModel = carModelRepository.findById(id).orElse(null);
        if (existingModel != null) {
            existingModel.setName(carModelDTO.getName());
            existingModel.setBrand(brandApiService.findById(carModelDTO.getBrandId()));
            existingModel.setVolume(volumeApiService.findById(carModelDTO.getVolumeId()));
            existingModel.setTransmission(transmissionApiService.findById(carModelDTO.getTransmissionId()));
            existingModel.setDrive(driveApiService.findEntityById(carModelDTO.getDriveId()));
            existingModel.setFuelType(fuelTypeApiService.findById(carModelDTO.getFuelTypeId()));
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
