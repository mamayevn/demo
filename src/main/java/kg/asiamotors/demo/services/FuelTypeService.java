package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.models.FuelType;
import kg.asiamotors.demo.repasitories.FuelTypeRepository;
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
    // Метод для поиска бренда по id
    public FuelType findById(int id) {
        return fuelTypeRepository.findById(id).orElse(null); // Если бренд не найден, возвращаем null
    }

}
