package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.models.FuelType;
import kg.asiamotors.demo.repository.FuelTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuelTypeService {
    private final FuelTypeRepository fuelTypeRepository;

    public FuelTypeService(FuelTypeRepository fuelTypeRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
    }

    public List<FuelTypeDTO> getAllFuelTypes() {
        return fuelTypeRepository.findAll()
                .stream()
                .map(fuelType -> new FuelTypeDTO(fuelType.getId(), fuelType.getName()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<FuelTypeDTO> getFuelTypeById(int id) {
        FuelType fuelType = fuelTypeRepository.findById(id).orElse(null);
        if (fuelType != null) {
            return ResponseEntity.ok(new FuelTypeDTO(fuelType.getId(), fuelType.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<FuelTypeDTO> createFuelType(FuelTypeDTO fuelTypeDTO) {
        FuelType fuelType = new FuelType();
        fuelType.setName(fuelTypeDTO.getName());
        fuelTypeRepository.save(fuelType);
        return ResponseEntity.ok(new FuelTypeDTO(fuelType.getId(), fuelType.getName()));
    }

    public ResponseEntity<FuelTypeDTO> updateFuelType(int id, FuelTypeDTO fuelTypeDTO) {
        FuelType existingFuelType = fuelTypeRepository.findById(id).orElse(null);
        if (existingFuelType != null) {
            existingFuelType.setName(fuelTypeDTO.getName());
            fuelTypeRepository.save(existingFuelType);
            return ResponseEntity.ok(new FuelTypeDTO(existingFuelType.getId(), existingFuelType.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> deleteFuelType(int id) {
        FuelType existingFuelType = fuelTypeRepository.findById(id).orElse(null);
        if (existingFuelType != null) {
            fuelTypeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    public FuelType findById(int id) {
        return fuelTypeRepository.findById(id).orElse(null);
    }
    public List<FuelTypeDTO> searchFuelTypesByName(String name) {
        return fuelTypeRepository.findByName(name)
                .stream()
                .map(fuelType -> new FuelTypeDTO(fuelType.getId(), fuelType.getName()))
                .collect(Collectors.toList());
    }
    public Page<FuelTypeDTO> getAllFuelTypeDto(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return fuelTypeRepository.findAllFuelTypeDtos(pageable);
    }
}
