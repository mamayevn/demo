package kg.asiamotors.demo.service;
import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.FuelType;
import kg.asiamotors.demo.repository.FuelTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FuelTypeServiceTest {

    private FuelTypeService fuelTypeService;

    @Mock
    private FuelTypeRepository fuelTypeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fuelTypeService = new FuelTypeService(fuelTypeRepository);
    }

    @Test
    void testGetAllFuelTypes() {
        FuelType fuelType = new FuelType(1, "Бензин");
        when(fuelTypeRepository.findAll()).thenReturn(Collections.singletonList(fuelType));

        List<FuelTypeDTO> fuelTypes = fuelTypeService.getAllFuelTypes();

        assertNotNull(fuelTypes);
        assertEquals(1, fuelTypes.size());
        assertEquals("Бензин", fuelTypes.get(0).getName());
        verify(fuelTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetFuelTypeById_Found() {
        FuelType fuelType = new FuelType(1, "Дизель");
        when(fuelTypeRepository.findById(1)).thenReturn(Optional.of(fuelType));

        ResponseEntity<FuelTypeDTO> response = fuelTypeService.getFuelTypeById(1);

        assertNotNull(response);
        assertEquals("Дизель", response.getBody().getName());
        verify(fuelTypeRepository, times(1)).findById(1);
    }

    @Test
    void testGetFuelTypeById_NotFound() {
        when(fuelTypeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fuelTypeService.getFuelTypeById(1));
        verify(fuelTypeRepository, times(1)).findById(1);
    }

    @Test
    void testCreateFuelType() {
        FuelTypeDTO fuelTypeDTO = new FuelTypeDTO(0, "Электро");
        FuelType fuelType = new FuelType(1, "Электро");

        when(fuelTypeRepository.save(any(FuelType.class))).thenReturn(fuelType);

        ResponseEntity<FuelTypeDTO> response = fuelTypeService.createFuelType(fuelTypeDTO);

        assertNotNull(response);
        assertEquals("Электро", response.getBody().getName());
        verify(fuelTypeRepository, times(1)).save(any(FuelType.class));
    }

    @Test
    void testUpdateFuelType() {
        FuelType existingFuelType = new FuelType(1, "Бензин");
        FuelTypeDTO fuelTypeDTO = new FuelTypeDTO(0, "Дизель");

        when(fuelTypeRepository.findById(1)).thenReturn(Optional.of(existingFuelType));
        when(fuelTypeRepository.save(any(FuelType.class))).thenReturn(existingFuelType);

        ResponseEntity<FuelTypeDTO> response = fuelTypeService.updateFuelType(1, fuelTypeDTO);

        assertNotNull(response);
        assertEquals("Дизель", response.getBody().getName());
        verify(fuelTypeRepository, times(1)).findById(1);
        verify(fuelTypeRepository, times(1)).save(any(FuelType.class));
    }

    @Test
    void testDeleteFuelType_Found() {
        FuelType existingFuelType = new FuelType(1, "Бензин");

        when(fuelTypeRepository.findById(1)).thenReturn(Optional.of(existingFuelType));

        ResponseEntity<Void> response = fuelTypeService.deleteFuelType(1);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(fuelTypeRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteFuelType_NotFound() {
        when(fuelTypeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fuelTypeService.deleteFuelType(1));
        verify(fuelTypeRepository, times(1)).findById(1);
        verify(fuelTypeRepository, never()).deleteById(anyInt());
    }
}
