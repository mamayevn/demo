package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.TransmissionDTO;
import kg.asiamotors.demo.service.TransmissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransmissionApiControllerTest {

    @Mock
    private TransmissionService transmissionService;

    @InjectMocks
    private TransmissionApiController transmissionApiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTransmissions_ShouldReturnListOfTransmissions() {
        List<TransmissionDTO> transmissions = List.of(
                new TransmissionDTO(1, "Ручной"),
                new TransmissionDTO(2, "Автомат")
        );
        when(transmissionService.getAllTransmissions()).thenReturn(transmissions);

        List<TransmissionDTO> result = transmissionApiController.getAllTransmissions();

        assertEquals(2, result.size());
        verify(transmissionService, times(1)).getAllTransmissions();
    }

    @Test
    void getTransmissionById_ShouldReturnTransmission_WhenExists() {
        TransmissionDTO transmission = new TransmissionDTO(1, "Ручной");
        when(transmissionService.getTransmissionById(1)).thenReturn(transmission);

        ResponseEntity<TransmissionDTO> response = transmissionApiController.getTransmissionById(1);

        assertEquals("Ручной", response.getBody().getName());
        verify(transmissionService, times(1)).getTransmissionById(1);
    }

    @Test
    void createTransmission_ShouldReturnCreatedTransmission() {
        TransmissionDTO transmissionDTO = new TransmissionDTO(0, "Вариатор");
        TransmissionDTO createdTransmission = new TransmissionDTO(1, "Вариатор");

        when(transmissionService.createTransmission(transmissionDTO)).thenReturn(ResponseEntity.ok(createdTransmission));

        ResponseEntity<TransmissionDTO> response = transmissionApiController.createTransmission(transmissionDTO);

        assertEquals(1, response.getBody().getId());
        assertEquals("Вариатор", response.getBody().getName());
        verify(transmissionService, times(1)).createTransmission(transmissionDTO);
    }

    @Test
    void updateTransmission_ShouldReturnUpdatedTransmission_WhenExists() {
        TransmissionDTO transmissionDTO = new TransmissionDTO(1, "Обновленный автомат");
        when(transmissionService.updateTransmission(1, transmissionDTO)).thenReturn(ResponseEntity.ok(transmissionDTO));

        ResponseEntity<TransmissionDTO> response = transmissionApiController.updateTransmission(1, transmissionDTO);

        assertEquals("Обновленный автомат", response.getBody().getName());
        verify(transmissionService, times(1)).updateTransmission(1, transmissionDTO);
    }

    @Test
    void deleteTransmission_ShouldReturnNoContent_WhenTransmissionExists() {
        when(transmissionService.deleteTransmission(1)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = transmissionApiController.deleteTransmission(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(transmissionService, times(1)).deleteTransmission(1);
    }

    @Test
    void searchTransmissionsByName_ShouldReturnListOfTransmissions_WhenFound() {
        List<TransmissionDTO> transmissions = List.of(
                new TransmissionDTO(1, "Ручной"),
                new TransmissionDTO(2, "Автомат")
        );
        when(transmissionService.searchTransmissionsByName("Авто")).thenReturn(transmissions);

        List<TransmissionDTO> result = transmissionApiController.searchTransmissionsByName("Авто");

        assertEquals(2, result.size());
        verify(transmissionService, times(1)).searchTransmissionsByName("Авто");
    }

    @Test
    void getAllTransmissionDto_ShouldReturnPagedTransmissions() {
        List<TransmissionDTO> transmissions = List.of(
                new TransmissionDTO(1, "Ручной"),
                new TransmissionDTO(2, "Автомат")
        );
        Page<TransmissionDTO> page = new PageImpl<>(transmissions);
        when(transmissionService.getAllTransmissionDto(0, 10)).thenReturn(page);

        ResponseEntity<Page<TransmissionDTO>> response = transmissionApiController.getAllTransmissionDto(0, 10);

        assertEquals(2, response.getBody().getContent().size());
        verify(transmissionService, times(1)).getAllTransmissionDto(0, 10);
    }
}
