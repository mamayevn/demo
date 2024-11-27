package kg.asiamotors.demo.repositories;

import kg.asiamotors.demo.dto.BrandDTO;
import kg.asiamotors.demo.models.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    List<Brand> findByNameContainingIgnoreCase(String name);

    @Query("select new kg.asiamotors.demo.dto.BrandDTO(b.name) from Brand b")
    Page<BrandDTO> findAllBrandDtos(Pageable pageable);

}
