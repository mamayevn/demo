package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.CustomerDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Customer;
import kg.asiamotors.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @CachePut(value = "customers", key = "#result.id")
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setEmail(customerDTO.getEmail());
        customer.setOccupation(customerDTO.getOccupation());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer = customerRepository.save(customer);
        return convertToDTO(customer);
    }

    @CachePut(value = "customers", key = "#id")
    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент с id " + id + " не найден"));

        existingCustomer.setFirstName(customerDTO.getFirstName());
        existingCustomer.setLastName(customerDTO.getLastName());
        existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setOccupation(customerDTO.getOccupation());
        existingCustomer.setBirthDate(customerDTO.getBirthDate());

        existingCustomer = customerRepository.save(existingCustomer);

        return convertToDTO(existingCustomer);
    }

    @Cacheable(value = "customers", key = "'allCustomers'")
    public List<CustomerDTO> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO findCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент с id " + id + " не найден"));
        return convertToDTO(customer);
    }


    @CacheEvict(value = "customers", key = "#id")
    public boolean deleteCustomer(int id) {
        if (!customerRepository.existsById(id)) {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setEmail(customer.getEmail());
        dto.setOccupation(customer.getOccupation());
        dto.setBirthDate(customer.getBirthDate());
        return dto;
    }

    @Cacheable(value = "customers", key = "'search:' + #query")
    public List<CustomerDTO> searchCustomers(String query) {
        List<Customer> customers = customerRepository.findByFirstNameOrLastNameOrPhoneNumber(query, query, query);
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "customers", key = "'page:' + #offset + ':' + #limit")
    public Page<CustomerDTO> getAllCustomerDto(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return customerRepository.findAllCustomerDtos(pageable);
    }
}
