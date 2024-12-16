package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    private BrandDTO brandDTO;
    private Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandDTO = new BrandDTO();
        brandDTO.setName("Тестируем бренд");

        brand = new Brand();
        brand.setName("Тестируем бренд");
    }

    @Test
    void findById_Success() {
        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        Brand foundBrand = brandService.findById(1);

        assertNotNull(foundBrand);
        assertEquals("Тестируем бренд", foundBrand.getName());
    }

    @Test
    void findById_NotFound() {
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            brandService.findById(1);
        });

        assertEquals("Бренд с id 1 не найден", thrown.getMessage());
    }

    @Test
    void findAllBrandDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BrandDTO> page = mock(Page.class);
        when(brandRepository.findAllBrandDtos(pageable)).thenReturn(page);

        Page<BrandDTO> result = brandService.findAllBrandDtos(0, 10);

        assertNotNull(result);
        verify(brandRepository).findAllBrandDtos(pageable);
    }

    @Test
    void createBrand() {
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandDTO createdBrand = brandService.createBrand(brandDTO);

        assertNotNull(createdBrand);
        assertEquals("Тестируем бренд", createdBrand.getName());
    }

    @Test
    void updateBrand_Success() {
        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        BrandDTO updatedBrand = brandService.updateBrand(1, brandDTO);

        assertNotNull(updatedBrand);
        assertEquals("Тестируем бренд", updatedBrand.getName());
    }

    @Test
    void updateBrand_NotFound() {
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            brandService.updateBrand(1, brandDTO);
        });

        assertEquals("Брeнд с id 1 не найден", thrown.getMessage());
    }

    @Test
    void deleteBrand_Success() {
        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));

        brandService.deleteBrand(1);

        verify(brandRepository).deleteById(1);
    }

    @Test
    void deleteBrand_NotFound() {
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            brandService.deleteBrand(1);
        });

        assertEquals("Брeнд с id 1 не найден", thrown.getMessage());
    }

    @Test
    void searchBrandsByName_Success() {
        when(brandRepository.findByNameContainingIgnoreCase("Тест")).thenReturn(Collections.singletonList(brand));

        List<BrandDTO> result = brandService.searchBrandsByName("Тест");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Тестируем бренд", result.get(0).getName());
    }

    @Test
    void searchBrandsByName_NotFound() {
        when(brandRepository.findByNameContainingIgnoreCase("Нет такого бренда")).thenReturn(Collections.emptyList());

        List<BrandDTO> result = brandService.searchBrandsByName("Нет такого бренда");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
