package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.service.DriveService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
public class DriveApiController {
    private final DriveService driveService;

    public DriveApiController(DriveService driveService) {
        this.driveService = driveService;
    }

    @GetMapping
    public List<DriveDTO> getAllDrives() {
        return driveService.getAllDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriveDTO> getDriveById(@PathVariable int id) {
        DriveDTO driveDTO = driveService.findById(id);
        if (driveDTO != null) {
            return ResponseEntity.ok(driveDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DriveDTO> createDrive(@RequestBody DriveDTO driveDTO) {
        DriveDTO createdDrive = driveService.createDrive(driveDTO);
        return ResponseEntity.ok(createdDrive);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriveDTO> updateDrive(@PathVariable int id, @RequestBody DriveDTO driveDTO) {
        DriveDTO updatedDrive = driveService.updateDrive(id, driveDTO);
        if (updatedDrive != null) {
            return ResponseEntity.ok(updatedDrive);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrive(@PathVariable int id) {
        boolean deleted = driveService.deleteDrive(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<DriveDTO>> searchDrives(@RequestParam String name) {
        List<DriveDTO> drives = driveService.searchDrivesByName(name);
        if (drives.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(drives);
    }
    @GetMapping("page")
    public ResponseEntity<Page<DriveDTO>> getAllDriveDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                               @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(driveService.getAllDriveDto(offset, limit));
    }
}
