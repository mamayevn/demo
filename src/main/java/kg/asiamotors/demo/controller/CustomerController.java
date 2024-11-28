package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.CustomerDTO;
import kg.asiamotors.demo.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "Получение всего списка клиентов")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение покупателя по ID")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        CustomerDTO customerDTO = customerService.findCustomerById(id);
        if (customerDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerDTO);
    }

    @PostMapping
    @Operation(summary = "Создать покупателя")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить покупателя")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        if (updatedCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить покупателя")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        boolean deleted = customerService.deleteCustomer(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    @Operation(summary = "Поиск пользователя")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam String query) {
        List<CustomerDTO> customers = customerService.searchCustomers(query);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("page")
    @Operation(summary = "Поиск пользователя с пагинацией")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomerDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                     @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(customerService.getAllCustomerDto(offset, limit));
    }
}
