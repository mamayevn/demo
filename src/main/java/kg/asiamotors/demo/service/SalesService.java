package kg.asiamotors.demo.service;

import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Sale;
import kg.asiamotors.demo.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private SaleRepository saleRepository;

    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public List<Sale> findAllSales() {
        return saleRepository.findAll();
    }
    public Page<Sale> findAllSales(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }
    public void delete(int id) {
        saleRepository.deleteById(id);
    }
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale findById(int id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продажа с id " + id + " не найдена"));
    }

    public void save(Sale sale) {
        saleRepository.save(sale);
    }
}