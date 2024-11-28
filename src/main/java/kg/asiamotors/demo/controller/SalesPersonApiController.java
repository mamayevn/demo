package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.SalesPersonDTO;
import kg.asiamotors.demo.service.SalesPersonService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salespersons")
@Tag(name = "SalesPerson")
public class SalesPersonApiController {

    private final SalesPersonService salesPersonService;

    public SalesPersonApiController(SalesPersonService salesPersonService) {
        this.salesPersonService = salesPersonService;
    }

    @GetMapping
    @Operation(summary = "Получение списка продавцов")
    public List<SalesPersonDTO> getAllSalesPersons() {
        return salesPersonService.findAllSalespersons();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение продавца по id")
    public ResponseEntity<SalesPersonDTO> getSalesPersonById(@PathVariable int id) {
        SalesPersonDTO salesPersonDTO = salesPersonService.findSalespersonById(id);
        if (salesPersonDTO != null) {
            return ResponseEntity.ok(salesPersonDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Создать продавца")
    public ResponseEntity<SalesPersonDTO> createSalesPerson(@RequestBody SalesPersonDTO salesPersonDTO) {
        SalesPersonDTO createdSalesPersonDTO = salesPersonService.createSalesPerson(salesPersonDTO);
        return ResponseEntity.ok(createdSalesPersonDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить продавца")
    public ResponseEntity<SalesPersonDTO> updateSalesPerson(@PathVariable int id, @RequestBody SalesPersonDTO salesPersonDTO) {
        SalesPersonDTO updatedSalesPersonDTO = salesPersonService.updateSalesPerson(id, salesPersonDTO);
        if (updatedSalesPersonDTO != null) {
            return ResponseEntity.ok(updatedSalesPersonDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить продавца")
    public ResponseEntity<Void> deleteSalesPerson(@PathVariable int id) {
        boolean isDeleted = salesPersonService.deleteSalesPerson(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/search")
    @Operation(summary = "Поиск продавца")
    public List<SalesPersonDTO> searchSalesPersons(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {
        return salesPersonService.searchSalesPersons(firstName, lastName, email);
    }
    @GetMapping("page")
    @Operation(summary = "Получение продавца с пагинацией")
    public ResponseEntity<Page<SalesPersonDTO>> getAllSalesPersonDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                           @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(salesPersonService.getAllSalesPersonDto(offset, limit));
    }
}
