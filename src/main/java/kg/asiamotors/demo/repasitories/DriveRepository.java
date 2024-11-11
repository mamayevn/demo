package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.models.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveRepository extends JpaRepository<Drive, Integer> {
}
