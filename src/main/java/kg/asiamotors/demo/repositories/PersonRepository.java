package kg.asiamotors.demo.repositories;

import kg.asiamotors.demo.dto.PersonDTO;
import kg.asiamotors.demo.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByNameContainingIgnoreCase(String name);
    @Query("select new kg.asiamotors.demo.dto.PersonDTO(name, age, email) from Person")
    Page<PersonDTO> findAllPersonDtos(Pageable pageable);
}
