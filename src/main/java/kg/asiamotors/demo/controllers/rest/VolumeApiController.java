package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.VolumeDTO;
import kg.asiamotors.demo.services.api.VolumeApiService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volumes")
public class VolumeApiController {
    private final VolumeApiService volumeApiService;

    public VolumeApiController(VolumeApiService volumeApiService) {
        this.volumeApiService = volumeApiService;
    }

    @GetMapping
    public List<VolumeDTO> getAllVolumes() {
        return volumeApiService.getAllVolumes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolumeDTO> getVolumeById(@PathVariable int id) {
        return volumeApiService.getVolumeById(id);
    }

    @PostMapping
    public ResponseEntity<VolumeDTO> createVolume(@RequestBody VolumeDTO volumeDTO) {
        return volumeApiService.createVolume(volumeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VolumeDTO> updateVolume(@PathVariable int id, @RequestBody VolumeDTO volumeDTO) {
        return volumeApiService.updateVolume(id, volumeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolume(@PathVariable int id) {
        return volumeApiService.deleteVolume(id);
    }

    @GetMapping("/search")
    public List<VolumeDTO> searchVolumesByName(@RequestParam String name) {
        return volumeApiService.searchByName(name);
    }
    @GetMapping("page")
    public ResponseEntity<Page<VolumeDTO>> getAllVolumeDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                                       @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(volumeApiService.getAllVolumeDto(offset, limit));
    }
}
