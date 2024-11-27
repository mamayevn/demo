package kg.asiamotors.demo.services.api;

import kg.asiamotors.demo.models.Sale;
import kg.asiamotors.demo.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SaleApiService {

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
        return saleRepository.findById(id).orElse(null);
    }

    public void save(Sale sale) {
        saleRepository.save(sale);
    }
}