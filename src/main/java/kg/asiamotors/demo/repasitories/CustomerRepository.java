package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
