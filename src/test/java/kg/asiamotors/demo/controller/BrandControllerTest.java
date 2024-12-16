package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandControllerTest {

    @Mock
    private BrandService brandService;

    @InjectMocks
    private BrandController brandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBrands_ShouldReturnListOfBrands() {
        List<BrandDTO> mockBrands = Arrays.asList(
                new BrandDTO("Brand1"),
                new BrandDTO("Brand2")
        );
        when(brandService.findAllBrands()).thenReturn(mockBrands);
        ResponseEntity<List<BrandDTO>> response = brandController.getAllBrands();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockBrands, response.getBody());
        verify(brandService, times(1)).findAllBrands();
    }

    @Test
    void createBrand_ShouldCreateAndReturnBrand() {
        BrandDTO mockBrand = new BrandDTO("Brand1");
        when(brandService.createBrand(mockBrand)).thenReturn(mockBrand);
        ResponseEntity<BrandDTO> response = brandController.createBrand(mockBrand);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockBrand, response.getBody());
        verify(brandService, times(1)).createBrand(mockBrand);
    }

    @Test
    void updateBrand_ShouldUpdateAndReturnBrand() {
        int brandId = 1;
        BrandDTO mockBrand = new BrandDTO("UpdatedBrand");
        when(brandService.updateBrand(brandId, mockBrand)).thenReturn(mockBrand);

        ResponseEntity<BrandDTO> response = brandController.updateBrand(brandId, mockBrand);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockBrand, response.getBody());
        verify(brandService, times(1)).updateBrand(brandId, mockBrand);
    }

    @Test
    void deleteBrand_ShouldDeleteBrand() {
        int brandId = 1;
        doNothing().when(brandService).deleteBrand(brandId);
        ResponseEntity<Void> response = brandController.deleteBrand(brandId);
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(brandService, times(1)).deleteBrand(brandId);
    }

    @Test
    void searchBrands_ShouldReturnMatchingBrands() {
        String searchName = "Brand";
        List<BrandDTO> mockBrands = Arrays.asList(
                new BrandDTO("Brand1"),
                new BrandDTO("Brand2")
        );
        when(brandService.searchBrandsByName(searchName)).thenReturn(mockBrands);
        ResponseEntity<List<BrandDTO>> response = brandController.searchBrands(searchName);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockBrands, response.getBody());
        verify(brandService, times(1)).searchBrandsByName(searchName);
    }
}
