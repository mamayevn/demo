package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.models.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    Optional<CarModel> findById(int id);
}
