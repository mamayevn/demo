package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.TransmissionDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Transmission;
import kg.asiamotors.demo.repository.TransmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransmissionServiceTest {

    @InjectMocks
    private TransmissionService transmissionService;

    @Mock
    private TransmissionRepository transmissionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransmissions() {
        Transmission transmission1 = new Transmission();
        transmission1.setId(1);
        transmission1.setName("Ручной");

        Transmission transmission2 = new Transmission();
        transmission2.setId(2);
        transmission2.setName("Автомат");

        when(transmissionRepository.findAll()).thenReturn(Arrays.asList(transmission1, transmission2));

        List<TransmissionDTO> result = transmissionService.getAllTransmissions();

        assertEquals(2, result.size());
        assertEquals("Ручной", result.get(0).getName());
        assertEquals("Автомат", result.get(1).getName());
        verify(transmissionRepository, times(1)).findAll();
    }

    @Test
    void testGetTransmissionById() {
        Transmission transmission = new Transmission();
        transmission.setId(1);
        transmission.setName("Ручной");
        when(transmissionRepository.findById(1)).thenReturn(Optional.of(transmission));
        TransmissionDTO result = transmissionService.getTransmissionById(1);
        assertEquals(1, result.getId());
        assertEquals("Ручной", result.getName());
        verify(transmissionRepository, times(1)).findById(1);
    }


    @Test
    void testGetTransmissionById_NotFound() {
        when(transmissionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transmissionService.getTransmissionById(1));
        verify(transmissionRepository, times(1)).findById(1);
    }

    public ResponseEntity<TransmissionDTO> createTransmission(TransmissionDTO transmissionDTO) {
        Transmission transmission = new Transmission();
        transmission.setName(transmissionDTO.getName());
        transmission = transmissionRepository.save(transmission);

        return ResponseEntity.ok(new TransmissionDTO(
                transmission.getId(),
                transmission.getName()
        ));
    }

    @Test
    void testUpdateTransmission() {
        Transmission existingTransmission = new Transmission();
        existingTransmission.setId(1);
        existingTransmission.setName("Ручной");

        TransmissionDTO dto = new TransmissionDTO(1, "Автомат");

        when(transmissionRepository.findById(1)).thenReturn(Optional.of(existingTransmission));
        when(transmissionRepository.save(any(Transmission.class))).thenReturn(existingTransmission);

        ResponseEntity<TransmissionDTO> result = transmissionService.updateTransmission(1, dto);

        assertEquals(1, result.getBody().getId());
        assertEquals("Автомат", result.getBody().getName());
        verify(transmissionRepository, times(1)).findById(1);
        verify(transmissionRepository, times(1)).save(existingTransmission);
    }

    @Test
    void testDeleteTransmission() {
        Transmission transmission = new Transmission();
        transmission.setId(1);

        when(transmissionRepository.findById(1)).thenReturn(Optional.of(transmission));
        doNothing().when(transmissionRepository).deleteById(1);

        ResponseEntity<Void> result = transmissionService.deleteTransmission(1);

        assertEquals(204, result.getStatusCodeValue());
        verify(transmissionRepository, times(1)).findById(1);
        verify(transmissionRepository, times(1)).deleteById(1);
    }

    @Test
    void testSearchTransmissionsByName() {
        Transmission transmission = new Transmission();
        transmission.setId(1);
        transmission.setName("Ручной");

        when(transmissionRepository.findByName("Ручной")).thenReturn(List.of(transmission));

        List<TransmissionDTO> result = transmissionService.searchTransmissionsByName("Ручной");

        assertEquals(1, result.size());
        assertEquals("Ручной", result.get(0).getName());
        verify(transmissionRepository, times(1)).findByName("Ручной");
    }

    @Test
    void testGetAllTransmissionDto() {
        TransmissionDTO dto = new TransmissionDTO(1, "Ручной");
        Page<TransmissionDTO> page = new PageImpl<>(List.of(dto));
        PageRequest pageable = PageRequest.of(0, 10);

        when(transmissionRepository.findAllTransmissionDtos(pageable)).thenReturn(page);

        Page<TransmissionDTO> result = transmissionService.getAllTransmissionDto(0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals("Ручной", result.getContent().get(0).getName());
        verify(transmissionRepository, times(1)).findAllTransmissionDtos(pageable);
    }
}
