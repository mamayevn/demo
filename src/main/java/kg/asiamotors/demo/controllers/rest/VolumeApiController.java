package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.VolumeDTO;
import kg.asiamotors.demo.services.VolumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volumes")
public class VolumeApiController {
    private final VolumeService volumeService;

    public VolumeApiController(VolumeService volumeService) {
        this.volumeService = volumeService;
    }

    @GetMapping
    public List<VolumeDTO> getAllVolumes() {
        return volumeService.getAllVolumes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolumeDTO> getVolumeById(@PathVariable int id) {
        return volumeService.getVolumeById(id);
    }

    @PostMapping
    public ResponseEntity<VolumeDTO> createVolume(@RequestBody VolumeDTO volumeDTO) {
        return volumeService.createVolume(volumeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VolumeDTO> updateVolume(@PathVariable int id, @RequestBody VolumeDTO volumeDTO) {
        return volumeService.updateVolume(id, volumeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolume(@PathVariable int id) {
        return volumeService.deleteVolume(id);
    }
}
