package kg.asiamotors.demo.service;

import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Sale;
import kg.asiamotors.demo.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalesServiceTest {

    @InjectMocks
    private SalesService salesService;

    @Mock
    private SaleRepository saleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSale() {
        Sale sale = new Sale();
        sale.setId(1);

        when(saleRepository.save(sale)).thenReturn(sale);

        Sale result = salesService.saveSale(sale);

        assertEquals(1, result.getId());
        verify(saleRepository, times(1)).save(sale);
    }

    @Test
    void testFindAllSales() {
        Sale sale1 = new Sale();
        Sale sale2 = new Sale();

        when(saleRepository.findAll()).thenReturn(Arrays.asList(sale1, sale2));

        List<Sale> result = salesService.findAllSales();

        assertEquals(2, result.size());
        verify(saleRepository, times(1)).findAll();
    }

    @Test
    void testFindAllSalesWithPagination() {
        Sale sale = new Sale();
        Page<Sale> page = new PageImpl<>(List.of(sale));
        Pageable pageable = PageRequest.of(0, 10);

        when(saleRepository.findAll(pageable)).thenReturn(page);

        Page<Sale> result = salesService.findAllSales(pageable);

        assertEquals(1, result.getContent().size());
        verify(saleRepository, times(1)).findAll(pageable);
    }

    @Test
    void testDeleteSale() {
        doNothing().when(saleRepository).deleteById(1);

        salesService.delete(1);

        verify(saleRepository, times(1)).deleteById(1);
    }

    @Test
    void testFindById() {
        Sale sale = new Sale();
        sale.setId(1);

        when(saleRepository.findById(1)).thenReturn(Optional.of(sale));

        Sale result = salesService.findById(1);

        assertEquals(1, result.getId());
        verify(saleRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(saleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> salesService.findById(1));
        verify(saleRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        Sale sale = new Sale();
        sale.setId(1);
        when(saleRepository.save(sale)).thenReturn(sale);
        salesService.save(sale);
        verify(saleRepository, times(1)).save(sale);
    }
}
