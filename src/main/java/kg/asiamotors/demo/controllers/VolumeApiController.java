package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.VolumeDTO;
import kg.asiamotors.demo.models.Volume;
import kg.asiamotors.demo.services.VolumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/volumes")
public class VolumeApiController {
    private final VolumeService volumeService;

    public VolumeApiController(VolumeService volumeService) {
        this.volumeService = volumeService;
    }

    @GetMapping
    public List<VolumeDTO> getAllVolumes() {
        return volumeService.findAll().stream()
                .map(volume -> new VolumeDTO(
                        volume.getId(),
                        volume.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolumeDTO> getVolumeById(@PathVariable int id) {
        Volume volume = volumeService.findById(id);
        if (volume != null) {
            return ResponseEntity.ok(new VolumeDTO(
                    volume.getId(),
                    volume.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<VolumeDTO> createVolume(@RequestBody VolumeDTO volumeDTO) {
        Volume volume = new Volume();
        volume.setName(volumeDTO.getName());

        volumeService.save(volume);

        return ResponseEntity.ok(new VolumeDTO(
                volume.getId(),
                volume.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VolumeDTO> updateVolume(@PathVariable int id, @RequestBody VolumeDTO volumeDTO) {
        Volume existingVolume = volumeService.findById(id);
        if (existingVolume != null) {
            existingVolume.setName(volumeDTO.getName());
            volumeService.save(existingVolume);

            return ResponseEntity.ok(new VolumeDTO(
                    existingVolume.getId(),
                    existingVolume.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolume(@PathVariable int id) {
        Volume existingVolume = volumeService.findById(id);
        if (existingVolume != null) {
            volumeService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
