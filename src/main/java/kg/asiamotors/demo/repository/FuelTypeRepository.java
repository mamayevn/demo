package kg.asiamotors.demo.repository;

import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.models.FuelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Integer> {
    List<FuelType> findByName(String name);
    @Query("select new kg.asiamotors.demo.dto.FuelTypeDTO(name) from FuelType")
    Page<FuelTypeDTO> findAllFuelTypeDtos(Pageable pageable);
}
