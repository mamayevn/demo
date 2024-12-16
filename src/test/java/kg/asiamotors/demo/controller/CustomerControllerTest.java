package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.CustomerDTO;
import kg.asiamotors.demo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        List<CustomerDTO> customers = List.of(new CustomerDTO("John", "Doe", "123456789", "john@example.com", "Engineer", null));
        when(customerService.findAllCustomers()).thenReturn(customers);
        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();
        assertEquals(1, response.getBody().size());
        verify(customerService, times(1)).findAllCustomers();
    }

    @Test
    void getCustomerById_ShouldReturnCustomer_WhenCustomerExists() {
        CustomerDTO customer = new CustomerDTO("Nurs", "Mamaev", "0704275142", "mamaev@mail.com", "Marketing", null);
        when(customerService.findCustomerById(1)).thenReturn(customer);
        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(1);
        assertEquals("Nurs", response.getBody().getFirstName());
        verify(customerService, times(1)).findCustomerById(1);
    }

    @Test
    void createCustomer_ShouldReturnCreatedCustomer() {
        CustomerDTO customerDTO = new CustomerDTO("Nurs", "Mamaev", "0704275142", "mamayevn@gmail.com", "Маркетолог", null);
        when(customerService.createCustomer(customerDTO)).thenReturn(customerDTO);
        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customerDTO);
        assertEquals("Nurs", response.getBody().getFirstName());
        verify(customerService, times(1)).createCustomer(customerDTO);
    }
}
