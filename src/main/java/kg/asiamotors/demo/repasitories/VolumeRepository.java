package kg.asiamotors.demo.repasitories;

import kg.asiamotors.demo.models.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {
}
