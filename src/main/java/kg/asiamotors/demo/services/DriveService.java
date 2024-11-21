package kg.asiamotors.demo.services;

import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.models.Drive;
import kg.asiamotors.demo.repasitories.DriveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriveService {
    private final DriveRepository driveRepository;

    public DriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public List<DriveDTO> getAllDrives() {
        return driveRepository.findAll()
                .stream()
                .map(drive -> new DriveDTO(drive.getId(), drive.getName()))
                .collect(Collectors.toList());
    }
    public Drive findEntityById(int id) {
        return driveRepository.findById(id).orElse(null);
    }
    // Новый метод для поиска по ID
    public DriveDTO findById(int id) {
        Drive drive = driveRepository.findById(id).orElse(null);
        if (drive != null) {
            return new DriveDTO(drive.getId(), drive.getName());
        }
        return null;
    }

    public DriveDTO createDrive(DriveDTO driveDTO) {
        Drive drive = new Drive();
        drive.setName(driveDTO.getName());
        drive = driveRepository.save(drive);
        return new DriveDTO(drive.getId(), drive.getName());
    }

    public DriveDTO updateDrive(int id, DriveDTO driveDTO) {
        Drive existingDrive = driveRepository.findById(id).orElse(null);
        if (existingDrive != null) {
            existingDrive.setName(driveDTO.getName());
            existingDrive = driveRepository.save(existingDrive);
            return new DriveDTO(existingDrive.getId(), existingDrive.getName());
        }
        return null;
    }
    public boolean deleteDrive(int id) {
        if (driveRepository.existsById(id)) {
            driveRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
