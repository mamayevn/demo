package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.CustomerDTO;
import kg.asiamotors.demo.services.api.CustomerApiService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerApiController {
    private final CustomerApiService customerApiService;

    public CustomerApiController(CustomerApiService customerApiService) {
        this.customerApiService = customerApiService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerApiService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        CustomerDTO customerDTO = customerApiService.findCustomerById(id);
        if (customerDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerDTO);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerApiService.createCustomer(customerDTO);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerApiService.updateCustomer(id, customerDTO);
        if (updatedCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        boolean deleted = customerApiService.deleteCustomer(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam String query) {
        List<CustomerDTO> customers = customerApiService.searchCustomers(query);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("page")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomerDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                     @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(customerApiService.getAllCustomerDto(offset, limit));
    }
}
