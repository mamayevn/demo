package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.models.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionRepository extends JpaRepository<Transmission, Integer> {
}
