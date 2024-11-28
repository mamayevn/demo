package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.repository.BrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand findById(int id) {
        log.info("Поиск бренда по id {} ", id);
        return brandRepository.findById(id).orElse(null);
    }

    public Page<BrandDTO> findAllBrandDtos(int offset, int limit) {
        log.info("Вывод списка брендов");
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
        log.info("Создание нового бренда: {} ", brandDTO);
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        Brand savedBrand = brandRepository.save(brand);
        log.info("Новый бренд успешно создан : {}", savedBrand);
        return brandDTO;
    }

    public BrandDTO updateBrand(int id, BrandDTO brandDTO) {
        log.info("Обновление бренда с id {} на {} ", id, brandDTO);
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Брeнд с id " + id + " не найден"));
        existingBrand.setName(brandDTO.getName());
        Brand updatedBrand = brandRepository.save(existingBrand);
        BrandDTO updatedBrandDTO = new BrandDTO();
        updatedBrandDTO.setName(updatedBrand.getName());
        log.info("Бренд успешно обновлен : {} ", updatedBrandDTO);
        return updatedBrandDTO;
    }

    public void deleteBrand(int id) {
        log.info("Попытка удаления бренда с id {} ", id);
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Брeнд с id " + id + " не найден"));
        log.info("Бренд с id {} успешно удален ", id);
        brandRepository.deleteById(id);
    }

    public List<BrandDTO> searchBrandsByName(String name) {
        log.info("Поиск бренда по названию {} ", name);
        List<Brand> brands = brandRepository.findByNameContainingIgnoreCase(name);

        List<BrandDTO> foundBrands =  brands.stream()
                .map(brand -> {
                    BrandDTO brandDTO = new BrandDTO();
                    brandDTO.setName(brand.getName());
                    return brandDTO;
                })
                .collect(Collectors.toList());
        log.info("Найденные бренды: {}", brands);
        return foundBrands;
    }

}
