package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.VolumeDTO;
import kg.asiamotors.demo.service.VolumeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volumes")
@Tag(name = "Volume")
public class VolumeApiController {
    private final VolumeService volumeApiService;

    public VolumeApiController(VolumeService volumeApiService) {
        this.volumeApiService = volumeApiService;
    }

    @GetMapping
    @Operation(summary = "Получение списка объема")
    public List<VolumeDTO> getAllVolumes() {
        return volumeApiService.getAllVolumes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение объема по id")
    public ResponseEntity<VolumeDTO> getVolumeById(@PathVariable int id) {
        return volumeApiService.getVolumeById(id);
    }

    @PostMapping
    @Operation(summary = "Создание объема")
    public ResponseEntity<VolumeDTO> createVolume(@RequestBody VolumeDTO volumeDTO) {
        return volumeApiService.createVolume(volumeDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить объем")
    public ResponseEntity<VolumeDTO> updateVolume(@PathVariable int id, @RequestBody VolumeDTO volumeDTO) {
        return volumeApiService.updateVolume(id, volumeDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить объем")
    public ResponseEntity<Void> deleteVolume(@PathVariable int id) {
        return volumeApiService.deleteVolume(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Найти объем по имени")
    public List<VolumeDTO> searchVolumesByName(@RequestParam String name) {
        return volumeApiService.searchByName(name);
    }
    @GetMapping("page")
    @Operation(summary = "Получение списка объема с пагинацией")
    public ResponseEntity<Page<VolumeDTO>> getAllVolumeDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                           @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(volumeApiService.getAllVolumeDto(offset, limit));
    }
}