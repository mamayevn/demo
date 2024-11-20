package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.SalesPersonDTO;
import kg.asiamotors.demo.models.SalesPerson;
import kg.asiamotors.demo.services.SalesPersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salespersons")
public class SalesPersonApiController {

    private final SalesPersonService salesPersonService;

    public SalesPersonApiController(SalesPersonService salesPersonService) {
        this.salesPersonService = salesPersonService;
    }

    @GetMapping
    public List<SalesPersonDTO> getAllSalesPersons() {
        return salesPersonService.findAllSalespersons()
                .stream()
                .map(salesPerson -> new SalesPersonDTO(
                        salesPerson.getId(),
                        salesPerson.getFirstName(),
                        salesPerson.getLastName(),
                        salesPerson.getPhoneNumber(),
                        salesPerson.getEmail(),
                        salesPerson.getPosition()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesPersonDTO> getSalesPersonById(@PathVariable int id) {
        SalesPerson salesPerson = salesPersonService.findAllSalespersons().get(id);
        if (salesPerson != null) {
            return ResponseEntity.ok(new SalesPersonDTO(
                    salesPerson.getId(),
                    salesPerson.getFirstName(),
                    salesPerson.getLastName(),
                    salesPerson.getPhoneNumber(),
                    salesPerson.getEmail(),
                    salesPerson.getPosition()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SalesPersonDTO> createSalesPerson(@RequestBody SalesPersonDTO salesPersonDTO) {
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFirstName(salesPersonDTO.getFirstName());
        salesPerson.setLastName(salesPersonDTO.getLastName());
        salesPerson.setPhoneNumber(salesPersonDTO.getPhoneNumber());
        salesPerson.setEmail(salesPersonDTO.getEmail());
        salesPerson.setPosition(salesPersonDTO.getPosition());

        salesPersonService.saveSalesperson(salesPerson);
        return ResponseEntity.ok(new SalesPersonDTO(
                salesPerson.getId(),
                salesPerson.getFirstName(),
                salesPerson.getLastName(),
                salesPerson.getPhoneNumber(),
                salesPerson.getEmail(),
                salesPerson.getPosition()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesPersonDTO> updateSalesPerson(@PathVariable int id, @RequestBody SalesPersonDTO salesPersonDTO) {
        SalesPerson existingSalesPerson = salesPersonService.findAllSalespersons().get(id);
        if (existingSalesPerson != null) {
            existingSalesPerson.setFirstName(salesPersonDTO.getFirstName());
            existingSalesPerson.setLastName(salesPersonDTO.getLastName());
            existingSalesPerson.setPhoneNumber(salesPersonDTO.getPhoneNumber());
            existingSalesPerson.setEmail(salesPersonDTO.getEmail());
            existingSalesPerson.setPosition(salesPersonDTO.getPosition());

            salesPersonService.saveSalesperson(existingSalesPerson);
            return ResponseEntity.ok(new SalesPersonDTO(
                    existingSalesPerson.getId(),
                    existingSalesPerson.getFirstName(),
                    existingSalesPerson.getLastName(),
                    existingSalesPerson.getPhoneNumber(),
                    existingSalesPerson.getEmail(),
                    existingSalesPerson.getPosition()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesPerson(@PathVariable int id) {
        SalesPerson existingSalesPerson = salesPersonService.findAllSalespersons().get(id);
        if (existingSalesPerson != null) {
            salesPersonService.deleteSalesperson(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
