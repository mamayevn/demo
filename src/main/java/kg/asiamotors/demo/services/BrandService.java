package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.repasitories.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
    public Brand findById(int id) {
        return brandRepository.findById(id).orElse(null);
    }
    public void delete(int id) {
        brandRepository.deleteById(id);
    }
}
