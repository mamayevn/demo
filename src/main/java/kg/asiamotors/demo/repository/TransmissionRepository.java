package kg.asiamotors.demo.repository;

import kg.asiamotors.demo.dto.TransmissionDTO;
import kg.asiamotors.demo.models.Transmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransmissionRepository extends JpaRepository<Transmission, Integer> {
    List<Transmission> findByName(String name);
    @Query("select new kg.asiamotors.demo.dto.TransmissionDTO(name) from Transmission")
    Page<TransmissionDTO> findAllTransmissionDtos(Pageable pageable);
}
