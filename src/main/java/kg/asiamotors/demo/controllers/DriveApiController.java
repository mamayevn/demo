package kg.asiamotors.demo.controllers;

import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.models.Drive;
import kg.asiamotors.demo.services.DriveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/drives")
public class DriveApiController {
    private final DriveService driveService;

    public DriveApiController(DriveService driveService) {
        this.driveService = driveService;
    }

    @GetMapping
    public List<DriveDTO> getAllDrives() {
        return driveService.findAll()
                .stream()
                .map(drive -> new DriveDTO(drive.getId(), drive.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriveDTO> getDriveById(@PathVariable int id) {
        Drive drive = driveService.findById(id);
        if (drive != null) {
            return ResponseEntity.ok(new DriveDTO(drive.getId(), drive.getName()));
        }
        return ResponseEntity.notFound().build();
    }

     @PostMapping
    public ResponseEntity<DriveDTO> createDrive(@RequestBody DriveDTO driveDTO) {
        Drive drive = new Drive();
        drive.setName(driveDTO.getName());
        driveService.save(drive);
        return ResponseEntity.ok(new DriveDTO(drive.getId(), drive.getName()));
    }

    // Обновление существующего Drive
    @PutMapping("/{id}")
    public ResponseEntity<DriveDTO> updateDrive(@PathVariable int id, @RequestBody DriveDTO driveDTO) {
        Drive existingDrive = driveService.findById(id);
        if (existingDrive != null) {
            existingDrive.setName(driveDTO.getName());
            driveService.save(existingDrive);
            return ResponseEntity.ok(new DriveDTO(existingDrive.getId(), existingDrive.getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrive(@PathVariable int id) {
        Drive existingDrive = driveService.findById(id);
        if (existingDrive != null) {
            driveService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
