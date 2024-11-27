package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.dto.CarDTO;
import kg.asiamotors.demo.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByPersonId(int personId);
    List<Car> findByBrand(String brand);

    @Query("select new kg.asiamotors.demo.dto.CarDTO(brand, model, engineVolume, year, price) from Car")
    Page<CarDTO> findAllCarDtos(Pageable pageable);

}
