package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.repasitories.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelService {
    private CarModelRepository carModelRepository;

    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    public List<CarModel> findAll() {
        return carModelRepository.findAll();
    }

    public CarModel save(CarModel carModel) {
        return carModelRepository.save(carModel);
    }
}
