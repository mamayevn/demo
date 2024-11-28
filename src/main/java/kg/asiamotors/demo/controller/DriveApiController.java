package kg.asiamotors.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.service.DriveService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
@Tag(name = "Drive")
public class DriveApiController {
    private final DriveService driveService;

    public DriveApiController(DriveService driveService) {
        this.driveService = driveService;
    }

    @GetMapping
    @Operation(summary = "Получение списка привода")
    public List<DriveDTO> getAllDrives() {
        return driveService.getAllDrives();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение списка привода по Id")
    public ResponseEntity<DriveDTO> getDriveById(@PathVariable int id) {
        DriveDTO driveDTO = driveService.findById(id);
        if (driveDTO != null) {
            return ResponseEntity.ok(driveDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Создание привода")
    public ResponseEntity<DriveDTO> createDrive(@RequestBody DriveDTO driveDTO) {
        DriveDTO createdDrive = driveService.createDrive(driveDTO);
        return ResponseEntity.ok(createdDrive);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление привода")
    public ResponseEntity<DriveDTO> updateDrive(@PathVariable int id, @RequestBody DriveDTO driveDTO) {
        DriveDTO updatedDrive = driveService.updateDrive(id, driveDTO);
        if (updatedDrive != null) {
            return ResponseEntity.ok(updatedDrive);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить привод")
    public ResponseEntity<Void> deleteDrive(@PathVariable int id) {
        boolean deleted = driveService.deleteDrive(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Найти привод")
    public ResponseEntity<List<DriveDTO>> searchDrives(@RequestParam String name) {
        List<DriveDTO> drives = driveService.searchDrivesByName(name);
        if (drives.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(drives);
    }
    @GetMapping("page")
    @Operation(summary = "Получение списка приводов пагинацией")
    public ResponseEntity<Page<DriveDTO>> getAllDriveDto(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                               @RequestParam(name = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(driveService.getAllDriveDto(offset, limit));
    }
}
