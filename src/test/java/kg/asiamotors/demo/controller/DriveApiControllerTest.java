package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.service.DriveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DriveApiControllerTest {

    @Mock
    private DriveService driveService;

    @InjectMocks
    private DriveApiController driveApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDrives_ShouldReturnListOfDrives() {
        List<DriveDTO> drives = List.of(new DriveDTO(1, "Привод1"), new DriveDTO(2, "Привод2"));
        when(driveService.getAllDrives()).thenReturn(drives);
        List<DriveDTO> result = driveApiController.getAllDrives();
        assertEquals(2, result.size());
        verify(driveService, times(1)).getAllDrives();
    }

    @Test
    void getDriveById_ShouldReturnDrive_WhenExists() {
        DriveDTO drive = new DriveDTO(1, "Привод1");
        when(driveService.findById(1)).thenReturn(drive);
        ResponseEntity<DriveDTO> response = driveApiController.getDriveById(1);
        assertEquals("Привод1", response.getBody().getName());
        verify(driveService, times(1)).findById(1);
    }

    @Test
    void createDrive_ShouldReturnCreatedDrive() {
        DriveDTO driveDTO = new DriveDTO(0, "Новый привод");
        DriveDTO createdDrive = new DriveDTO(1, "Новый привод");
        when(driveService.createDrive(driveDTO)).thenReturn(createdDrive);
        ResponseEntity<DriveDTO> response = driveApiController.createDrive(driveDTO);
        assertEquals(1, response.getBody().getId());
        assertEquals("Новый привод", response.getBody().getName());
        verify(driveService, times(1)).createDrive(driveDTO);
    }

    @Test
    void updateDrive_ShouldReturnUpdatedDrive_WhenExists() {
        DriveDTO driveDTO = new DriveDTO(1, "Обновленный привод");
        when(driveService.updateDrive(1, driveDTO)).thenReturn(driveDTO);
        ResponseEntity<DriveDTO> response = driveApiController.updateDrive(1, driveDTO);
        assertEquals("Обновленный привод", response.getBody().getName());
        verify(driveService, times(1)).updateDrive(1, driveDTO);
    }

    @Test
    void deleteDrive_ShouldReturnNoContent_WhenDriveExists() {
        when(driveService.deleteDrive(1)).thenReturn(true);
        ResponseEntity<Void> response = driveApiController.deleteDrive(1);
        assertEquals(204, response.getStatusCodeValue());
        verify(driveService, times(1)).deleteDrive(1);
    }
}
