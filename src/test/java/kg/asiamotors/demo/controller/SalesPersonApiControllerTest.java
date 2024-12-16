package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.SalesPersonDTO;
import kg.asiamotors.demo.service.SalesPersonService;
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

class SalesPersonApiControllerTest {

    @Mock
    private SalesPersonService salesPersonService;

    @InjectMocks
    private SalesPersonApiController salesPersonApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSalesPersons() {
        SalesPersonDTO salesPersonDTO = new SalesPersonDTO(1, "Nurs", "Mamaev", "0704275142", "mamaev@gmail.com", "Manager");

        when(salesPersonService.findAllSalespersons()).thenReturn(Arrays.asList(salesPersonDTO));

        List<SalesPersonDTO> result = salesPersonApiController.getAllSalesPersons();

        assertEquals(1, result.size());
        assertEquals("Nurs", result.get(0).getFirstName());
    }

    @Test
    void testGetSalesPersonById_Success() {
        SalesPersonDTO salesPersonDTO = new SalesPersonDTO(1, "Nurs", "Mamaev", "0704275142", "mamayevn@mail.com", "Manager");

        when(salesPersonService.findSalespersonById(1)).thenReturn(salesPersonDTO);

        ResponseEntity<SalesPersonDTO> response = salesPersonApiController.getSalesPersonById(1);

        assertNotNull(response.getBody());
        assertEquals("Nurs", response.getBody().getFirstName());
    }

    @Test
    void testGetSalesPersonById_NotFound() {
        when(salesPersonService.findSalespersonById(1)).thenReturn(null);

        ResponseEntity<SalesPersonDTO> response = salesPersonApiController.getSalesPersonById(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}
