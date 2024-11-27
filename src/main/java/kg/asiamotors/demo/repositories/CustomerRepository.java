package kg.asiamotors.demo.repositories;

import kg.asiamotors.demo.dto.CustomerDTO;
import kg.asiamotors.demo.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByFirstNameOrLastNameOrPhoneNumber(String firstName, String lastName, String phoneNumber);

    @Query("select new kg.asiamotors.demo.dto.CustomerDTO(firstName, lastName, phoneNumber, email, occupation, birthDate) from Customer")
    Page<CustomerDTO> findAllCustomerDtos(Pageable pageable);

}
