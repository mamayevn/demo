package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.FuelType;
import kg.asiamotors.demo.repository.FuelTypeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "fuelTypes", key = "'allFuelTypes'")
    public List<FuelTypeDTO> getAllFuelTypes() {
        return fuelTypeRepository.findAll()
                .stream()
                .map(fuelType -> new FuelTypeDTO(fuelType.getId(), fuelType.getName()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<FuelTypeDTO> getFuelTypeById(int id) {
        FuelType fuelType = fuelTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип топлива с id " + id + " не найден"));

        FuelTypeDTO fuelTypeDTO = new FuelTypeDTO(fuelType.getId(), fuelType.getName());
        return ResponseEntity.ok(fuelTypeDTO);
    }

    @Cacheable(value = "fuelTypes", key = "#id")
    public FuelTypeDTO getFuelTypeByIdCache(int id) {
        FuelType fuelType = fuelTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип топлива с id " + id + " не найден"));
        return new FuelTypeDTO(fuelType.getId(), fuelType.getName());
    }

    @CacheEvict(value = "fuelTypes", key = "'allFuelTypes'")
    public ResponseEntity<FuelTypeDTO> createFuelType(FuelTypeDTO fuelTypeDTO) {
        FuelType fuelType = new FuelType();
        fuelType.setName(fuelTypeDTO.getName());
        fuelTypeRepository.save(fuelType);
        return ResponseEntity.ok(new FuelTypeDTO(fuelType.getId(), fuelType.getName()));
    }

    @CacheEvict(value = "fuelTypes", key = "#id")
    public ResponseEntity<FuelTypeDTO> updateFuelType(int id, FuelTypeDTO fuelTypeDTO) {
        FuelType existingFuelType = fuelTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип топлива с id " + id + " не найден"));

        existingFuelType.setName(fuelTypeDTO.getName());
        fuelTypeRepository.save(existingFuelType);

        FuelTypeDTO updatedFuelTypeDTO = new FuelTypeDTO(existingFuelType.getId(), existingFuelType.getName());
        return ResponseEntity.ok(updatedFuelTypeDTO);
    }

    @CacheEvict(value = "fuelTypes", key = "#id")
    public ResponseEntity<Void> deleteFuelType(int id) {
        FuelType existingFuelType = fuelTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип топлива с id " + id + " не найден"));

        fuelTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public FuelType findById(int id) {
        return fuelTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип топлива с id " + id + " не найден"));
    }

    @Cacheable(value = "fuelTypes", key = "#name")
    public List<FuelTypeDTO> searchFuelTypesByName(String name) {
        return fuelTypeRepository.findByName(name)
                .stream()
                .map(fuelType -> new FuelTypeDTO(fuelType.getId(), fuelType.getName()))
                .collect(Collectors.toList());
    }
    @Cacheable(value = "fuelTypes", key = "'page_' + #offset + '_'+ #limit")
    public Page<FuelTypeDTO> getAllFuelTypeDto(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return fuelTypeRepository.findAllFuelTypeDtos(pageable);
    }
}
