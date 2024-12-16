package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.CustomerDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Customer;
import kg.asiamotors.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_ShouldReturnCreatedCustomer() {
        CustomerDTO customerDTO = new CustomerDTO("Nurs", "Mamaev", "0704275142", "mamaev@mail.com", "Маркетолог", LocalDate.of(1985, 5, 20));
        Customer customer = new Customer(1, "Nurs", "Mamaev", "0704275142", "mamaev@mail.com", "Маркетолог", LocalDate.of(1985, 5, 20));

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.createCustomer(customerDTO);

        assertEquals("Nurs", result.getFirstName());
        assertEquals("Mamaev", result.getLastName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void findCustomerById_ShouldReturnCustomer_WhenCustomerExists() {
        Customer customer = new Customer(1, "Nurs", "Mamaev", "0704275142", "mamaev@mail.com", "Marketing", LocalDate.of(1985, 5, 20));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        CustomerDTO result = customerService.findCustomerById(1);
        assertEquals("Nurs", result.getFirstName());
        assertEquals("Mamaev", result.getLastName());
    }

    @Test
    void findCustomerById_ShouldThrowException_WhenCustomerDoesNotExist() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerById(1));
    }

    @Test
    void deleteCustomer_ShouldReturnTrue_WhenCustomerExists() {
        when(customerRepository.existsById(1)).thenReturn(true);
        boolean result = customerService.deleteCustomer(1);
        assertTrue(result);
        verify(customerRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteCustomer_ShouldReturnFalse_WhenCustomerDoesNotExist() {
        when(customerRepository.existsById(1)).thenReturn(false);
        boolean result = customerService.deleteCustomer(1);
        assertFalse(result);
        verify(customerRepository, never()).deleteById(1);
    }
}
