package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.VolumeDTO;
import kg.asiamotors.demo.service.VolumeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VolumeApiControllerTest {

    @Mock
    private VolumeService volumeService;

    @InjectMocks
    private VolumeApiController volumeApiController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllVolumes() {
        VolumeDTO volume1 = new VolumeDTO(1, "v4");
        VolumeDTO volume2 = new VolumeDTO(2, "v6");
        List<VolumeDTO> volumes = Arrays.asList(volume1, volume2);
        when(volumeService.getAllVolumes()).thenReturn(volumes);

        List<VolumeDTO> result = volumeApiController.getAllVolumes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("v4", result.get(0).getName());
        assertEquals("v6", result.get(1).getName());
    }

    @Test
    public void testGetVolumeById() {
        int volumeId = 1;
        VolumeDTO volumeDTO = new VolumeDTO(volumeId, "v4");
        when(volumeService.getVolumeById(volumeId)).thenReturn(ResponseEntity.ok(volumeDTO));

        ResponseEntity<VolumeDTO> result = volumeApiController.getVolumeById(volumeId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("v4", result.getBody().getName());
    }

    @Test
    public void testCreateVolume() {
        VolumeDTO volumeDTO = new VolumeDTO(1, "Новый обьем");
        when(volumeService.createVolume(volumeDTO)).thenReturn(ResponseEntity.ok(volumeDTO));

        ResponseEntity<VolumeDTO> result = volumeApiController.createVolume(volumeDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Новый обьем", result.getBody().getName());
    }

    @Test
    public void testUpdateVolume() {
        int volumeId = 1;
        VolumeDTO volumeDTO = new VolumeDTO(volumeId, "Обновленный обьем");
        when(volumeService.updateVolume(volumeId, volumeDTO)).thenReturn(ResponseEntity.ok(volumeDTO));

        ResponseEntity<VolumeDTO> result = volumeApiController.updateVolume(volumeId, volumeDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Обновленный обьем", result.getBody().getName());
    }

    @Test
    public void testDeleteVolume() {
        int volumeId = 1;
        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();

        when(volumeService.deleteVolume(volumeId)).thenReturn(responseEntity);

        ResponseEntity<Void> result = volumeApiController.deleteVolume(volumeId);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testSearchVolumesByName() {
        String name = "Обьем";
        VolumeDTO volumeDTO = new VolumeDTO(1, name);
        List<VolumeDTO> volumes = Arrays.asList(volumeDTO);
        when(volumeService.searchByName(name)).thenReturn(volumes);

        List<VolumeDTO> result = volumeApiController.searchVolumesByName(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Обьем", result.get(0).getName());
    }

    @Test
    public void testGetAllVolumeDtoWithPagination() {
        int offset = 0;
        int limit = 10;
        VolumeDTO volume1 = new VolumeDTO(1, "v4");
        VolumeDTO volume2 = new VolumeDTO(2, "v6");
        List<VolumeDTO> volumes = Arrays.asList(volume1, volume2);
        Page<VolumeDTO> volumePage = new PageImpl<>(volumes, PageRequest.of(offset, limit), volumes.size());
        when(volumeService.getAllVolumeDto(offset, limit)).thenReturn(volumePage);

        ResponseEntity<Page<VolumeDTO>> result = volumeApiController.getAllVolumeDto(offset, limit);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().getContent().size());
    }
}
