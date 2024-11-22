package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Drive;
import kg.asiamotors.demo.repasitories.DriveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriveService {
    private final DriveRepository driveRepository;

    public DriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }
    public void save(Drive drive) {
        driveRepository.save(drive);
    }

    public List<Drive> findAll() {
        return driveRepository.findAll();
    }
    public Drive findById(int id) {
        return driveRepository.findById(id).orElse(null);
    }
    public void delete(int id) {
        driveRepository.deleteById(id);
    }
}