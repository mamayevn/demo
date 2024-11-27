package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@Tag(name = "Brands")
@SecurityRequirement(name = "JWT")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(summary = "Получение списка брендов")
    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        List<BrandDTO> brands = brandService.findAllBrands();
        return ResponseEntity.ok(brands);
    }

    @Operation(summary = "Получение списка брендов с пагинацией")
    @GetMapping("/page")
    public ResponseEntity<Page<BrandDTO>> getAllBrandDto(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(brandService.findAllBrandDtos(offset, limit));
    }

    @Operation(summary = "Создание нового бренда")
    @PostMapping("/add")
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.createBrand(brandDTO));
    }

    @Operation(summary = "Получение бренда по id ")
    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable int id, @RequestBody BrandDTO brandDTO) {
        BrandDTO updatedBrand = brandService.updateBrand(id, brandDTO);
        return ResponseEntity.ok(updatedBrand);
    }

    @Operation(summary = "Удаление бренда по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable int id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Поиск бренда по названию")
    @GetMapping("/search")
    public ResponseEntity<List<BrandDTO>> searchBrands(@RequestParam String name) {
        List<BrandDTO> brands = brandService.searchBrandsByName(name);
        return ResponseEntity.ok(brands);
    }
}
