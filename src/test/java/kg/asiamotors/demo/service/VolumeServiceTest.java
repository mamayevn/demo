package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.VolumeDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Volume;
import kg.asiamotors.demo.repository.VolumeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VolumeServiceTest {

    @Mock
    private VolumeRepository volumeRepository;

    @InjectMocks
    private VolumeService volumeService;

    private Volume volume;
    private VolumeDTO volumeDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        volume = new Volume();
        volume.setId(1);
        volume.setName("Volume 1");

        volumeDTO = new VolumeDTO(volume.getId(), volume.getName());
    }

    @Test
    public void testGetVolumeById_Success() {
        int id = 1;
        when(volumeRepository.findById(id)).thenReturn(Optional.of(volume));
        ResponseEntity<VolumeDTO> result = volumeService.getVolumeById(id);
        assertNotNull(result);
        assertEquals(volume.getId(), result.getBody().getId());
        assertEquals(volume.getName(), result.getBody().getName());
    }

    @Test
    public void testGetVolumeById_NotFound() {
        int id = 1;
        when(volumeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> volumeService.getVolumeById(id));
    }

    @Test
    public void testCreateVolume() {
        VolumeDTO volumeDTOToCreate = new VolumeDTO(0, "New Volume");
        Volume volumeToSave = new Volume();
        volumeToSave.setName(volumeDTOToCreate.getName());
        when(volumeRepository.save(any(Volume.class))).thenReturn(volumeToSave);
        ResponseEntity<VolumeDTO> result = volumeService.createVolume(volumeDTOToCreate);
        assertNotNull(result);
        assertEquals("New Volume", result.getBody().getName());
    }

    @Test
    public void testUpdateVolume() {
        int id = 1;
        VolumeDTO updatedVolumeDTO = new VolumeDTO(id, "Updated Volume");
        when(volumeRepository.findById(id)).thenReturn(Optional.of(volume));
        ResponseEntity<VolumeDTO> result = volumeService.updateVolume(id, updatedVolumeDTO);
        assertNotNull(result);
        assertEquals("Updated Volume", result.getBody().getName());
        assertEquals(id, result.getBody().getId());
    }

    @Test
    public void testDeleteVolume() {
         int id = 1;
        when(volumeRepository.findById(id)).thenReturn(Optional.of(volume));
        doNothing().when(volumeRepository).deleteById(id);
         ResponseEntity<Void> result = volumeService.deleteVolume(id);
            assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue());
        verify(volumeRepository, times(1)).deleteById(id);
    }

    @Test
    public void testSearchByName() {
        String name = "Volume 1";
        when(volumeRepository.findByName(name)).thenReturn(List.of(volume));
        List<VolumeDTO> result = volumeService.searchByName(name);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Volume 1", result.get(0).getName());
    }
}
