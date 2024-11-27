package kg.asiamotors.demo.repository;

import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.models.Drive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriveRepository extends JpaRepository<Drive, Integer> {
    List<Drive> findByName(String name);
    @Query("select new kg.asiamotors.demo.dto.DriveDTO(name) from Drive")
    Page<DriveDTO> findAllDriveDtos(Pageable pageable);
}
