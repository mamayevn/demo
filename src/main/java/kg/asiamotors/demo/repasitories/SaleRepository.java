package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
}

