package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.services.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/add_brands")
public class BrandApiController {

    private final BrandService brandService;

    public BrandApiController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        List<BrandDTO> brands = brandService.findAll().stream()
                .map(brand -> {
                    BrandDTO dto = new BrandDTO();
                    dto.setName(brand.getName());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brandService.save(brand);
        return ResponseEntity.ok(brand);
    }

    // Обновление бренда
    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable int id, @RequestBody BrandDTO brandDTO) {
        Brand existingBrand = brandService.findById(id);
        if (existingBrand == null) {
            return ResponseEntity.notFound().build();
        }

        existingBrand.setName(brandDTO.getName());
        brandService.save(existingBrand);
        return ResponseEntity.ok(existingBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable int id) {
        Brand existingBrand = brandService.findById(id);
        if (existingBrand == null) {
            return ResponseEntity.notFound().build();
        }

        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
