package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.dto.VolumeDTO;
import kg.asiamotors.demo.models.Volume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {
    List<Volume> findByName(String name);
    @Query("select new kg.asiamotors.demo.dto.VolumeDTO(name) from Volume")
    Page<VolumeDTO> findAllVolumeDtos(Pageable pageable);
}
