package kg.asiamotors.demo.services;

import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.repasitories.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand findById(int id) {
        return brandRepository.findById(id).orElse(null);
    }

    public List<BrandDTO> findAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brand -> {
                    BrandDTO brandDTO = new BrandDTO();
                    brandDTO.setName(brand.getName());
                    return brandDTO;
                })
                .collect(Collectors.toList());
    }

    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        Brand savedBrand = brandRepository.save(brand);
        BrandDTO savedBrandDTO = new BrandDTO();
        savedBrandDTO.setName(savedBrand.getName());
        return savedBrandDTO;
    }

    public BrandDTO updateBrand(int id, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(id).orElse(null);
        if (existingBrand == null) {
            return null;
        }
        existingBrand.setName(brandDTO.getName());
        Brand updatedBrand = brandRepository.save(existingBrand);
        BrandDTO updatedBrandDTO = new BrandDTO();
        updatedBrandDTO.setName(updatedBrand.getName());
        return updatedBrandDTO;
    }

    public boolean deleteBrand(int id) {
        Brand existingBrand = brandRepository.findById(id).orElse(null);
        if (existingBrand == null) {
            return false;
        }
        brandRepository.deleteById(id);
        return true;
    }
}
