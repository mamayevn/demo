package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.SalesPerson;
import kg.asiamotors.demo.repositories.SalesPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesPersonService {
    private final SalesPersonRepository salesPersonRepository;

    @Autowired
    public SalesPersonService(SalesPersonRepository salesPersonRepository) {
        this.salesPersonRepository = salesPersonRepository;
    }
    public List<SalesPerson> findAllSalespersons() {
        return salesPersonRepository.findAll();
    }

    public Optional<SalesPerson> findSalespersonById(int id) {
        return salesPersonRepository.findById(id);
    }

    public void saveSalesperson(SalesPerson salesperson) {
        salesPersonRepository.save(salesperson);
    }

    public void deleteSalesperson(int id) {
        salesPersonRepository.deleteById(id);
    }
}