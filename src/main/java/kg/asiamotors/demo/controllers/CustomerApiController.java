package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.CustomerDTO;
import kg.asiamotors.demo.models.Customer;
import kg.asiamotors.demo.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerApiController {
    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.findAllCustomers().stream()
                .map(customer -> {
                    CustomerDTO dto = new CustomerDTO();
                    dto.setFirstName(customer.getFirstName());
                    dto.setLastName(customer.getLastName());
                    dto.setPhoneNumber(customer.getPhoneNumber());
                    dto.setEmail(customer.getEmail());
                    dto.setOccupation(customer.getOccupation());
                    dto.setBirthDate(customer.getBirthDate());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setEmail(customerDTO.getEmail());
        customer.setOccupation(customerDTO.getOccupation());
        customer.setBirthDate(customerDTO.getBirthDate());
        customerService.saveCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO) {
        Customer existingCustomer = customerService.findCustomerById(id);
        if (existingCustomer == null) {
            return ResponseEntity.notFound().build();
        }

        existingCustomer.setFirstName(customerDTO.getFirstName());
        existingCustomer.setLastName(customerDTO.getLastName());
        existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setOccupation(customerDTO.getOccupation());
        existingCustomer.setBirthDate(customerDTO.getBirthDate());
        customerService.saveCustomer(existingCustomer);
        return ResponseEntity.ok(existingCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        Customer existingCustomer = customerService.findCustomerById(id);
        if (existingCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}
