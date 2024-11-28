package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.VolumeDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Volume;
import kg.asiamotors.demo.repository.VolumeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolumeService {
    private final VolumeRepository volumeRepository;

    public VolumeService(VolumeRepository volumeRepository) {
        this.volumeRepository = volumeRepository;
    }

    public List<VolumeDTO> getAllVolumes() {
        return volumeRepository.findAll().stream()
                .map(volume -> new VolumeDTO(
                        volume.getId(),
                        volume.getName()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<VolumeDTO> getVolumeById(int id) {
        Volume volume = findById(id);
        return ResponseEntity.ok(new VolumeDTO(volume.getId(), volume.getName()));
    }

    public ResponseEntity<VolumeDTO> createVolume(VolumeDTO volumeDTO) {
        Volume volume = new Volume();
        volume.setName(volumeDTO.getName());
        save(volume);
        return ResponseEntity.ok(new VolumeDTO(
                volume.getId(),
                volume.getName()));
    }

    public ResponseEntity<VolumeDTO> updateVolume(int id, VolumeDTO volumeDTO) {
        Volume existingVolume = findById(id);

        existingVolume.setName(volumeDTO.getName());
        save(existingVolume);

        return ResponseEntity.ok(new VolumeDTO(existingVolume.getId(), existingVolume.getName()));
    }


    public ResponseEntity<Void> deleteVolume(int id) {
        Volume existingVolume = findById(id);
        delete(id);
        return ResponseEntity.noContent().build();
    }

    private void save(Volume volume) {
        volumeRepository.save(volume);
    }

    public Volume findById(int id) {
        return volumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объем с id " + id + " не найден"));
    }


    private void delete(int id) {
        volumeRepository.deleteById(id);
    }

    public List<VolumeDTO> searchByName(String name) {
        return volumeRepository.findByName(name)
                .stream()
                .map(volume -> new VolumeDTO(volume.getId(), volume.getName()))
                .collect(Collectors.toList());
    }
    public Page<VolumeDTO> getAllVolumeDto(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return volumeRepository.findAllVolumeDtos(pageable);
    }
}
