package kg.asiamotors.demo.repository;

import kg.asiamotors.demo.dto.SalesPersonDTO;
import kg.asiamotors.demo.models.SalesPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SalesPersonRepository extends JpaRepository<SalesPerson, Integer> {
    List<SalesPerson> findByFirstNameOrLastNameOrEmail(String firstName, String lastName, String email);
    @Query("select new kg.asiamotors.demo.dto.SalesPersonDTO(firstName, lastName, phoneNumber, email, position) from SalesPerson")
    Page<SalesPersonDTO> findAllSalesPersonDtos(Pageable pageable);
}
