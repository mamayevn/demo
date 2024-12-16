package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.DriveDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Drive;
import kg.asiamotors.demo.repository.DriveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriveServiceTest {

    @Mock
    private DriveRepository driveRepository;

    @InjectMocks
    private DriveService driveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDrives_ShouldReturnAllDrives() {
        Drive drive1 = new Drive(1, "Передний привод");
        Drive drive2 = new Drive(2, "Полный привод");

        when(driveRepository.findAll()).thenReturn(Arrays.asList(drive1, drive2));

        List<DriveDTO> drives = driveService.getAllDrives();

        assertEquals(2, drives.size());
        assertEquals("Передний привод", drives.get(0).getName());
        assertEquals("Полный привод", drives.get(1).getName());
        verify(driveRepository, times(1)).findAll();
    }

    @Test
    void findEntityById_ShouldReturnDriveEntity() {
        Drive drive = new Drive(1, "Передний привод");
        when(driveRepository.findById(1)).thenReturn(Optional.of(drive));

        Drive result = driveService.findEntityById(1);

        assertNotNull(result);
        assertEquals("Передний привод", result.getName());
        verify(driveRepository, times(1)).findById(1);
    }

    @Test
    void findEntityById_ShouldThrowExceptionIfNotFound() {
        when(driveRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> driveService.findEntityById(1));
        verify(driveRepository, times(1)).findById(1);
    }

    @Test
    void findById_ShouldReturnDriveDTO() {
        Drive drive = new Drive(1, "Передний привод");
        when(driveRepository.findById(1)).thenReturn(Optional.of(drive));

        DriveDTO result = driveService.findById(1);

        assertNotNull(result, "Должно быть не null");
        assertEquals("Передний привод", result.getName(), "Наименование должно быть: 'Передний привод'.");
        verify(driveRepository, times(1)).findById(1);
    }


    @Test
    void createDrive_ShouldSaveAndReturnDrive() {
        Drive drive = new Drive(0, "Задний привод");
        Drive savedDrive = new Drive(1, "Задний привод");

        when(driveRepository.save(ArgumentMatchers.any(Drive.class))).thenReturn(savedDrive);

        DriveDTO driveDTO = new DriveDTO(0, "Задний привод");
        DriveDTO result = driveService.createDrive(driveDTO);

        assertEquals("Задний привод", result.getName());
        verify(driveRepository, times(1)).save(any(Drive.class));
    }

    @Test
    void updateDrive_ShouldUpdateAndReturnDrive() {
        Drive existingDrive = new Drive(1, "передний привод");
        when(driveRepository.findById(1)).thenReturn(Optional.of(existingDrive));
        when(driveRepository.save(any(Drive.class))).thenReturn(existingDrive);

        DriveDTO driveDTO = new DriveDTO(1, "Обновленный привод");
        DriveDTO result = driveService.updateDrive(1, driveDTO);

        assertEquals("Обновленный привод", result.getName());
        verify(driveRepository, times(1)).findById(1);
        verify(driveRepository, times(1)).save(any(Drive.class));
    }

    @Test
    void updateDrive_ShouldThrowExceptionIfNotFound() {
        when(driveRepository.findById(1)).thenReturn(Optional.empty());

        DriveDTO driveDTO = new DriveDTO(1, "Обновленный привод");

        assertThrows(ResourceNotFoundException.class, () -> driveService.updateDrive(1, driveDTO));
        verify(driveRepository, times(1)).findById(1);
        verify(driveRepository, never()).save(any(Drive.class));
    }

    @Test
    void deleteDrive_ShouldReturnTrueIfDeleted() {
        when(driveRepository.existsById(1)).thenReturn(true);

        boolean result = driveService.deleteDrive(1);

        assertTrue(result);
        verify(driveRepository, times(1)).existsById(1);
        verify(driveRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteDrive_ShouldReturnFalseIfNotFound() {
        when(driveRepository.existsById(1)).thenReturn(false);

        boolean result = driveService.deleteDrive(1);

        assertFalse(result);
        verify(driveRepository, times(1)).existsById(1);
        verify(driveRepository, never()).deleteById(anyInt());
    }

    @Test
    void searchDrivesByName_ShouldReturnMatchingDrives() {
        Drive drive = new Drive(1, "Передний привод");
        when(driveRepository.findByName("Передний привод")).thenReturn(List.of(drive));

        List<DriveDTO> result = driveService.searchDrivesByName("Передний привод");

        assertEquals(1, result.size());
        assertEquals("Передний привод", result.get(0).getName());
        verify(driveRepository, times(1)).findByName("Передний привод");
    }

    @Test
    void getAllDriveDto_ShouldReturnPagedDrives() {
        DriveDTO driveDTO = new DriveDTO(1, "Передний привод");
        Page<DriveDTO> page = new PageImpl<>(List.of(driveDTO));

        when(driveRepository.findAllDriveDtos(PageRequest.of(0, 5))).thenReturn(page);

        Page<DriveDTO> result = driveService.getAllDriveDto(0, 5);

        assertEquals(1, result.getTotalElements());
        assertEquals("Передний привод", result.getContent().get(0).getName());
        verify(driveRepository, times(1)).findAllDriveDtos(PageRequest.of(0, 5));
    }
}
