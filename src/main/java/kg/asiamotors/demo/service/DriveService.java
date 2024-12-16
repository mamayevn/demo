package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Drive;
import kg.asiamotors.demo.repository.DriveRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriveService {
    private final DriveRepository driveRepository;

    public DriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    @Cacheable(value = "drives", key = "'allDrives'")
    public List<DriveDTO> getAllDrives() {
        return driveRepository.findAll()
                .stream()
                .map(drive -> new DriveDTO(drive.getId(), drive.getName()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "drives", key = "#id")
    public Drive findEntityById(int id) {
        return driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Привод с id " + id + " не найден"));
    }

    @Cacheable(value = "drives", key = "#id")
    public DriveDTO findById(int id) {
        Drive drive = driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Привод с id " + id + " не найден"));

        return new DriveDTO(drive.getId(), drive.getName());
    }

    @CachePut(value = "drives", key = "#result.id")
    public DriveDTO createDrive(DriveDTO driveDTO) {
        Drive drive = new Drive();
        drive.setName(driveDTO.getName());
        drive = driveRepository.save(drive);
        return new DriveDTO(drive.getId(), drive.getName());
    }

    @CachePut(value = "drives", key = "#id")
    public DriveDTO updateDrive(int id, DriveDTO driveDTO) {
        Drive existingDrive = driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Привод с id " + id + " не найден"));

        existingDrive.setName(driveDTO.getName());
        existingDrive = driveRepository.save(existingDrive);

        return new DriveDTO(existingDrive.getId(), existingDrive.getName());
    }

    @CacheEvict(value = "drives", key = "#id")
    public boolean deleteDrive(int id) {
        if (driveRepository.existsById(id)) {
            driveRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Cacheable(value = "drives", key = "'searchByName:' + #name")
    public List<DriveDTO> searchDrivesByName(String name) {
        List<Drive> drives = driveRepository.findByName(name);
        return drives.stream()
                .map(drive -> new DriveDTO(drive.getId(), drive.getName()))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "drives", key = "'page:' + #offset + ':' + #limit")
    public Page<DriveDTO> getAllDriveDto(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return driveRepository.findAllDriveDtos(pageable);
    }
}
