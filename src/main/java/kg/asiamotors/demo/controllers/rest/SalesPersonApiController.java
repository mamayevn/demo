package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.SalesPersonDTO;
import kg.asiamotors.demo.services.api.SalesPersonApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salespersons")
public class SalesPersonApiController {

    private final SalesPersonApiService salesPersonApiService;

    public SalesPersonApiController(SalesPersonApiService salesPersonApiService) {
        this.salesPersonApiService = salesPersonApiService;
    }

    @GetMapping
    public List<SalesPersonDTO> getAllSalesPersons() {
        return salesPersonApiService.findAllSalespersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesPersonDTO> getSalesPersonById(@PathVariable int id) {
        SalesPersonDTO salesPersonDTO = salesPersonApiService.findSalespersonById(id);
        if (salesPersonDTO != null) {
            return ResponseEntity.ok(salesPersonDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SalesPersonDTO> createSalesPerson(@RequestBody SalesPersonDTO salesPersonDTO) {
        SalesPersonDTO createdSalesPersonDTO = salesPersonApiService.createSalesPerson(salesPersonDTO);
        return ResponseEntity.ok(createdSalesPersonDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesPersonDTO> updateSalesPerson(@PathVariable int id, @RequestBody SalesPersonDTO salesPersonDTO) {
        SalesPersonDTO updatedSalesPersonDTO = salesPersonApiService.updateSalesPerson(id, salesPersonDTO);
        if (updatedSalesPersonDTO != null) {
            return ResponseEntity.ok(updatedSalesPersonDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesPerson(@PathVariable int id) {
        boolean isDeleted = salesPersonApiService.deleteSalesPerson(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
