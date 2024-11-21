package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.SalesPersonDTO;
import kg.asiamotors.demo.services.SalesPersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salespersons")
public class SalesPersonApiController {

    private final SalesPersonService salesPersonService;

    public SalesPersonApiController(SalesPersonService salesPersonService) {
        this.salesPersonService = salesPersonService;
    }

    @GetMapping
    public List<SalesPersonDTO> getAllSalesPersons() {
        return salesPersonService.findAllSalespersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesPersonDTO> getSalesPersonById(@PathVariable int id) {
        SalesPersonDTO salesPersonDTO = salesPersonService.findSalespersonById(id);
        if (salesPersonDTO != null) {
            return ResponseEntity.ok(salesPersonDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SalesPersonDTO> createSalesPerson(@RequestBody SalesPersonDTO salesPersonDTO) {
        SalesPersonDTO createdSalesPersonDTO = salesPersonService.createSalesPerson(salesPersonDTO);
        return ResponseEntity.ok(createdSalesPersonDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesPersonDTO> updateSalesPerson(@PathVariable int id, @RequestBody SalesPersonDTO salesPersonDTO) {
        SalesPersonDTO updatedSalesPersonDTO = salesPersonService.updateSalesPerson(id, salesPersonDTO);
        if (updatedSalesPersonDTO != null) {
            return ResponseEntity.ok(updatedSalesPersonDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesPerson(@PathVariable int id) {
        boolean isDeleted = salesPersonService.deleteSalesPerson(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
