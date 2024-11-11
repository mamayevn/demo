package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.models.Car;
import kg.asiamotors.demo.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByPersonId(int personId);
    List<Car> findByBrand(String brand);
}
