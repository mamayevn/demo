package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.services.api.BrandApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandApiController {

    private final BrandApiService brandApiService;

    public BrandApiController(BrandApiService brandApiService) {
        this.brandApiService = brandApiService;
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        List<BrandDTO> brands = brandApiService.findAllBrands();
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandApiService.createBrand(brandDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable int id, @RequestBody BrandDTO brandDTO) {
        BrandDTO updatedBrand = brandApiService.updateBrand(id, brandDTO);
        if (updatedBrand == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable int id) {
        if (!brandApiService.deleteBrand(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
