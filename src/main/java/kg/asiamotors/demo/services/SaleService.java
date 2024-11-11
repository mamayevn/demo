package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Sale;
import kg.asiamotors.demo.models.SalesPerson;
import kg.asiamotors.demo.repasitories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
