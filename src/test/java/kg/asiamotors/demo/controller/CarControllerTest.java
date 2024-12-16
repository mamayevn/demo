package kg.asiamotors.demo.controller;

import kg.asiamotors.demo.dto.CarDTO;
import kg.asiamotors.demo.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCars_ShouldReturnListOfCars() {
        List<CarDTO> mockCars = Arrays.asList(
                new CarDTO("Brand1", "Model1", 2.0, 2020, 25000),
                new CarDTO("Brand2", "Model2", 3.0, 2021, 30000)
        );
        when(carService.getAllCars()).thenReturn(mockCars);
        ResponseEntity<List<CarDTO>> response = carController.getAllCars();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCars, response.getBody());
        verify(carService, times(1)).getAllCars();
    }

    @Test
    void getAllCarDto_ShouldReturnPagedCars() {
        List<CarDTO> mockCars = Arrays.asList(
                new CarDTO("Brand1", "Model1", 2.0, 2020, 25000),
                new CarDTO("Brand2", "Model2", 3.0, 2021, 30000)
        );
        Page<CarDTO> mockPage = new PageImpl<>(mockCars, PageRequest.of(0, 10), mockCars.size());
        when(carService.getAllCarDto(0, 10)).thenReturn(mockPage);
        ResponseEntity<Page<CarDTO>> response = carController.getAllCarDto(0, 10);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPage, response.getBody());
        verify(carService, times(1)).getAllCarDto(0, 10);
    }

    @Test
    void createCar_ShouldCreateAndReturnCar() {
        CarDTO mockCar = new CarDTO("Brand1", "Model1", 2.0, 2020, 25000);
        when(carService.createCar(mockCar)).thenReturn(mockCar);
        ResponseEntity<CarDTO> response = carController.createCar(mockCar);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCar, response.getBody());
        verify(carService, times(1)).createCar(mockCar);
    }

    @Test
    void updateCar_ShouldUpdateAndReturnCar() {
        int carId = 1;
        CarDTO mockCar = new CarDTO("UpdatedBrand", "UpdatedModel", 2.5, 2022, 27000);
        when(carService.updateCar(carId, mockCar)).thenReturn(mockCar);
        ResponseEntity<CarDTO> response = carController.updateCar(carId, mockCar);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCar, response.getBody());
        verify(carService, times(1)).updateCar(carId, mockCar);
    }

    @Test
    void deleteCar_ShouldDeleteCar() {
        int carId = 1;
        when(carService.deleteCar(carId)).thenReturn(true);
        ResponseEntity<Void> response = carController.deleteCar(carId);
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(carService, times(1)).deleteCar(carId);
    }

    @Test
    void searchCarsByBrand_ShouldReturnMatchingCars() {
        String brand = "Brand1";
        List<CarDTO> mockCars = Arrays.asList(
                new CarDTO("Brand1", "Model1", 2.0, 2020, 25000)
        );
        when(carService.searchCarsByBrand(brand)).thenReturn(mockCars);
        ResponseEntity<List<CarDTO>> response = carController.searchCarsByBrand(brand);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCars, response.getBody());
        verify(carService, times(1)).searchCarsByBrand(brand);
    }

    @Test
    void searchCarsByBrand_ShouldReturnNotFoundIfNoCars() {
        String brand = "NonExistingBrand";
        when(carService.searchCarsByBrand(brand)).thenReturn(Arrays.asList());
        ResponseEntity<List<CarDTO>> response = carController.searchCarsByBrand(brand);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(carService, times(1)).searchCarsByBrand(brand);
    }
}
