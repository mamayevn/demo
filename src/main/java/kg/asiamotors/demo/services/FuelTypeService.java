package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.FuelType;
import kg.asiamotors.demo.repositories.FuelTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTypeService {
    private final FuelTypeRepository fuelTypeRepository;

    public FuelTypeService(FuelTypeRepository fuelTypeRepository) {
        this.fuelTypeRepository = fuelTypeRepository;
    }
    public void save(FuelType fuelType) {
        fuelTypeRepository.save(fuelType);
    }

    public List<FuelType> findAll() {
        return fuelTypeRepository.findAll();
    }
    public FuelType findById(int id) {
        return fuelTypeRepository.findById(id).orElse(null); // Если бренд не найден, возвращаем null
    }
    public void delete(int id) {
        fuelTypeRepository.deleteById(id);
    }
}