package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.models.SalesPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesPersonRepository extends JpaRepository<SalesPerson, Integer> {
}
