package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Sale;
import kg.asiamotors.demo.repasitories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SaleService {

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
}