package kg.asiamotors.demo.repository;

import kg.asiamotors.demo.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    Page<Sale> findAll(Pageable pageable);
}

