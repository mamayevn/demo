package kg.asiamotors.demo.controllers.rest;

import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.services.api.DriveApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
public class DriveApiController {
    private final DriveApiService driveApiService;

    public DriveApiController(DriveApiService driveApiService) {
        this.driveApiService = driveApiService;
    }

    @GetMapping
    public List<DriveDTO> getAllDrives() {
        return driveApiService.getAllDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriveDTO> getDriveById(@PathVariable int id) {
        DriveDTO driveDTO = driveApiService.findById(id);
        if (driveDTO != null) {
            return ResponseEntity.ok(driveDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DriveDTO> createDrive(@RequestBody DriveDTO driveDTO) {
        DriveDTO createdDrive = driveApiService.createDrive(driveDTO);
        return ResponseEntity.ok(createdDrive);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriveDTO> updateDrive(@PathVariable int id, @RequestBody DriveDTO driveDTO) {
        DriveDTO updatedDrive = driveApiService.updateDrive(id, driveDTO);
        if (updatedDrive != null) {
            return ResponseEntity.ok(updatedDrive);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrive(@PathVariable int id) {
        boolean deleted = driveApiService.deleteDrive(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
