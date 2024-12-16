package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.SalesPersonDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.SalesPerson;
import kg.asiamotors.demo.repository.SalesPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalesPersonServiceTest {

    @InjectMocks
    private SalesPersonService salesPersonService;

    @Mock
    private SalesPersonRepository salesPersonRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllSalespersons() {
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setId(1);
        salesPerson.setFirstName("Nurs");
        salesPerson.setLastName("Mamaev");
        salesPerson.setEmail("mamayevn@mail.ru");
        salesPerson.setPhoneNumber("0704275142");
        salesPerson.setPosition("Manager");

        when(salesPersonRepository.findAll()).thenReturn(List.of(salesPerson));

        List<SalesPersonDTO> result = salesPersonService.findAllSalespersons();

        assertEquals(1, result.size());
        assertEquals("Nurs", result.get(0).getFirstName());
    }

    @Test
    void testFindSalespersonById() {
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setId(1);
        salesPerson.setFirstName("Nurs");

        when(salesPersonRepository.findById(1)).thenReturn(Optional.of(salesPerson));

        SalesPersonDTO result = salesPersonService.findSalespersonById(1);

        assertEquals("Nurs", result.getFirstName());
    }

    @Test
    void testFindSalespersonById_NotFound() {
        when(salesPersonRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> salesPersonService.findSalespersonById(1));
    }

    @Test
    void testCreateSalesPerson() {
        SalesPersonDTO salesPersonDTO = new SalesPersonDTO();
        salesPersonDTO.setFirstName("Нурс");
        salesPersonDTO.setLastName("Мамаев");
        salesPersonDTO.setEmail("mamaev@gmail.com");
        salesPersonDTO.setPhoneNumber("9379992");
        salesPersonDTO.setPosition("Продажник");

        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setId(1);
        salesPerson.setFirstName("Нурс");
        salesPerson.setLastName("Мамаев");

        when(salesPersonRepository.save(any(SalesPerson.class))).thenReturn(salesPerson);

        SalesPersonDTO result = salesPersonService.createSalesPerson(salesPersonDTO);

        assertEquals("Нурс", result.getFirstName());
        assertEquals("Мамаев", result.getLastName());
    }

    @Test
    void testUpdateSalesPerson() {
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setId(1);
        salesPerson.setFirstName("Нурс");

        SalesPersonDTO updatedDTO = new SalesPersonDTO();
        updatedDTO.setFirstName("Нерс");
        updatedDTO.setLastName("Мамаив");

        when(salesPersonRepository.findById(1)).thenReturn(Optional.of(salesPerson));
        when(salesPersonRepository.save(any(SalesPerson.class))).thenReturn(salesPerson);

        SalesPersonDTO result = salesPersonService.updateSalesPerson(1, updatedDTO);

        assertEquals("Нерс", result.getFirstName());
    }

    @Test
    void testDeleteSalesPerson() {
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setId(1);

        when(salesPersonRepository.findById(1)).thenReturn(Optional.of(salesPerson));

        boolean result = salesPersonService.deleteSalesPerson(1);

        assertTrue(result);
        verify(salesPersonRepository, times(1)).deleteById(1);
    }

    @Test
    void testSearchSalesPersons() {
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setId(1);
        salesPerson.setFirstName("Албу");
        salesPerson.setLastName("Мамаева");

        when(salesPersonRepository.findByFirstNameOrLastNameOrEmail("Албу", null, null))
                .thenReturn(List.of(salesPerson));

        List<SalesPersonDTO> result = salesPersonService.searchSalesPersons("Албу", null, null);

        assertEquals(1, result.size());
        assertEquals("Албу", result.get(0).getFirstName());
    }

    @Test
    void testGetAllSalesPersonDto() {
        SalesPersonDTO salesPersonDTO = new SalesPersonDTO();
        salesPersonDTO.setFirstName("Альбу");
        Page<SalesPersonDTO> page = new PageImpl<>(List.of(salesPersonDTO));

        when(salesPersonRepository.findAllSalesPersonDtos(PageRequest.of(0, 10))).thenReturn(page);

        Page<SalesPersonDTO> result = salesPersonService.getAllSalesPersonDto(0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals("Альбу", result.getContent().get(0).getFirstName());
    }
}
