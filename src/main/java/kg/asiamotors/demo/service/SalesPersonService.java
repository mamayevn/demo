package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.SalesPersonDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.SalesPerson;
import kg.asiamotors.demo.repository.SalesPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesPersonService {
    private final SalesPersonRepository salesPersonRepository;

    @Autowired
    public SalesPersonService(SalesPersonRepository salesPersonRepository) {
        this.salesPersonRepository = salesPersonRepository;
    }

    public List<SalesPersonDTO> findAllSalespersons() {
        return salesPersonRepository.findAll()
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

    public SalesPersonDTO findSalespersonById(int id) {
        SalesPerson salesPerson = salesPersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продавец с id " + id + " не найден"));

        return new SalesPersonDTO(
                salesPerson.getId(),
                salesPerson.getFirstName(),
                salesPerson.getLastName(),
                salesPerson.getPhoneNumber(),
                salesPerson.getEmail(),
                salesPerson.getPosition());
    }

    public SalesPersonDTO createSalesPerson(SalesPersonDTO salesPersonDTO) {
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setFirstName(salesPersonDTO.getFirstName());
        salesPerson.setLastName(salesPersonDTO.getLastName());
        salesPerson.setPhoneNumber(salesPersonDTO.getPhoneNumber());
        salesPerson.setEmail(salesPersonDTO.getEmail());
        salesPerson.setPosition(salesPersonDTO.getPosition());

        salesPersonRepository.save(salesPerson);

        return new SalesPersonDTO(
                salesPerson.getId(),
                salesPerson.getFirstName(),
                salesPerson.getLastName(),
                salesPerson.getPhoneNumber(),
                salesPerson.getEmail(),
                salesPerson.getPosition());
    }

    public SalesPersonDTO updateSalesPerson(int id, SalesPersonDTO salesPersonDTO) {
        SalesPerson existingSalesPerson = salesPersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продавец с id " + id + " не найден"));

        existingSalesPerson.setFirstName(salesPersonDTO.getFirstName());
        existingSalesPerson.setLastName(salesPersonDTO.getLastName());
        existingSalesPerson.setPhoneNumber(salesPersonDTO.getPhoneNumber());
        existingSalesPerson.setEmail(salesPersonDTO.getEmail());
        existingSalesPerson.setPosition(salesPersonDTO.getPosition());

        salesPersonRepository.save(existingSalesPerson);

        return new SalesPersonDTO(
                existingSalesPerson.getId(),
                existingSalesPerson.getFirstName(),
                existingSalesPerson.getLastName(),
                existingSalesPerson.getPhoneNumber(),
                existingSalesPerson.getEmail(),
                existingSalesPerson.getPosition());
    }

    public boolean deleteSalesPerson(int id) {
        Optional<SalesPerson> existingSalesPersonOptional = salesPersonRepository.findById(id);
        if (existingSalesPersonOptional.isPresent()) {
            salesPersonRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<SalesPersonDTO> searchSalesPersons(String firstName, String lastName, String email) {
        return salesPersonRepository.findByFirstNameOrLastNameOrEmail(firstName, lastName, email)
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
    public Page<SalesPersonDTO> getAllSalesPersonDto(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return salesPersonRepository.findAllSalesPersonDtos(pageable);
    }
}
