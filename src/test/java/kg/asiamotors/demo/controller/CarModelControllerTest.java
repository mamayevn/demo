package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.CarModelDTO;
import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.service.CarModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class CarModelControllerTest {

    @InjectMocks
    private CarModelController carModelController;

    @Mock
    private CarModelService carModelService;

    private CarModelDTO carModelDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carModelDTO = new CarModelDTO();
        carModelDTO.setName("Model S");
        carModelDTO.setBrandId(1);
        carModelDTO.setVolumeId(1);
        carModelDTO.setTransmissionId(1);
        carModelDTO.setDriveId(1);
        carModelDTO.setFuelTypeId(1);
    }

    @Test
    void testGetAllCarModels() {
        List<CarModelDTO> carModelDTOList = Arrays.asList(carModelDTO);
        when(carModelService.getAllCarModels()).thenReturn(carModelDTOList);
        ResponseEntity<List<CarModelDTO>> response = carModelController.getAllCarModels();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(carModelService, times(1)).getAllCarModels();
    }

    @Test
    void testCreateCarModel() {
        CarModel carModel = new CarModel();
        carModel.setName("Model X");
        when(carModelService.createCarModel(any(CarModelDTO.class))).thenReturn(carModel);
        ResponseEntity<CarModel> response = carModelController.createCarModel(carModelDTO);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Model X", response.getBody().getName());
        verify(carModelService, times(1)).createCarModel(any(CarModelDTO.class));
    }

    @Test
    void testUpdateCarModel() {
        CarModel updatedCarModel = new CarModel();
        updatedCarModel.setName("Updated Model S");
        when(carModelService.updateCarModel(eq(1), any(CarModelDTO.class))).thenReturn(updatedCarModel);
        ResponseEntity<CarModel> response = carModelController.updateCarModel(1, carModelDTO);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Model S", response.getBody().getName());
        verify(carModelService, times(1)).updateCarModel(eq(1), any(CarModelDTO.class));
    }

    @Test
    void testDeleteCarModel() {
        when(carModelService.deleteCarModel(1)).thenReturn(true);
        ResponseEntity<Void> response = carModelController.deleteCarModel(1);
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(carModelService, times(1)).deleteCarModel(1);
    }

    @Test
    void testDeleteCarModelNotFound() {
        when(carModelService.deleteCarModel(1)).thenReturn(false);
        ResponseEntity<Void> response = carModelController.deleteCarModel(1);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(carModelService, times(1)).deleteCarModel(1);
    }
}
