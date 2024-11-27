package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.repository.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
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

    public Page<BrandDTO> findAllBrandDtos(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return brandRepository.findAllBrandDtos(pageable);
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
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Брeнд с id " + id + " не найден"));
        existingBrand.setName(brandDTO.getName());
        Brand updatedBrand = brandRepository.save(existingBrand);
        BrandDTO updatedBrandDTO = new BrandDTO();
        updatedBrandDTO.setName(updatedBrand.getName());
        return updatedBrandDTO;
    }

    public void deleteBrand(int id) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Брeнд с id " + id + " не найден"));
        brandRepository.deleteById(id);
    }
    public List<BrandDTO> searchBrandsByName(String name) {
        List<Brand> brands = brandRepository.findByNameContainingIgnoreCase(name);
        return brands.stream()
                .map(brand -> {
                    BrandDTO brandDTO = new BrandDTO();
                    brandDTO.setName(brand.getName());
                    return brandDTO;
                })
                .collect(Collectors.toList());
    }

}
