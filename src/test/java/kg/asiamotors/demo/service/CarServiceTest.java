package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.CarDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.Car;
import kg.asiamotors.demo.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCars_ShouldReturnAllCars() {
        Car car1 = new Car();
        car1.setBrand("Toyota");
        car1.setModel("Camry");
        car1.setEngineVolume(2.5);
        car1.setYear(2021);
        car1.setPrice(30000);

        Car car2 = new Car();
        car2.setBrand("Honda");
        car2.setModel("Civic");
        car2.setEngineVolume(1.8);
        car2.setYear(2020);
        car2.setPrice(20000);
        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));
        List<CarDTO> cars = carService.getAllCars();
        assertEquals(2, cars.size());
        assertEquals("Toyota", cars.get(0).getBrand());
        assertEquals("Honda", cars.get(1).getBrand());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void getAllCarDto_ShouldReturnPagedCars() {
        CarDTO carDTO = new CarDTO("Toyota", "Camry", 2.5, 2021, 30000);
        Page<CarDTO> page = new PageImpl<>(List.of(carDTO));

        when(carRepository.findAllCarDtos(PageRequest.of(0, 5))).thenReturn(page);

        Page<CarDTO> result = carService.getAllCarDto(0, 5);

        assertEquals(1, result.getTotalElements());
        assertEquals("Toyota", result.getContent().get(0).getBrand());
        verify(carRepository, times(1)).findAllCarDtos(PageRequest.of(0, 5));
    }

    @Test
    void createCar_ShouldSaveAndReturnCar() {
        Car car = new Car(0, "Toyota", "Camry", 2.5, 2021, 30000);
        Car savedCar = new Car(1, "Toyota", "Camry", 2.5, 2021, 30000);

        when(carRepository.save(any(Car.class))).thenReturn(savedCar);

        CarDTO carDTO = new CarDTO("Toyota", "Camry", 2.5, 2021, 30000);

        CarDTO result = carService.createCar(carDTO);

        assertEquals("Toyota", result.getBrand());
        assertEquals("Camry", result.getModel());
        verify(carRepository, times(1)).save(any(Car.class));
    }


    @Test
    void updateCar_ShouldUpdateAndReturnCar() {
        Car existingCar = new Car(1, "Toyota", "Camry", 2.5, 2021, 30000);
        when(carRepository.findById(1)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(any(Car.class))).thenReturn(existingCar);

        CarDTO carDTO = new CarDTO("Honda", "Civic", 1.8, 2020, 25000);
        CarDTO updatedCar = carService.updateCar(1, carDTO);

        assertEquals("Honda", updatedCar.getBrand());
        verify(carRepository, times(1)).findById(1);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void updateCar_ShouldThrowExceptionIfNotFound() {
        when(carRepository.findById(1)).thenReturn(Optional.empty());

        CarDTO carDTO = new CarDTO("Honda", "Civic", 1.8, 2020, 25000);

        assertThrows(ResourceNotFoundException.class, () -> carService.updateCar(1, carDTO));
        verify(carRepository, times(1)).findById(1);
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void deleteCar_ShouldReturnTrueIfDeleted() {
        when(carRepository.existsById(1)).thenReturn(true);

        boolean result = carService.deleteCar(1);

        assertTrue(result);
        verify(carRepository, times(1)).existsById(1);
        verify(carRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteCar_ShouldReturnFalseIfNotFound() {
        when(carRepository.existsById(1)).thenReturn(false);

        boolean result = carService.deleteCar(1);

        assertFalse(result);
        verify(carRepository, times(1)).existsById(1);
        verify(carRepository, never()).deleteById(anyInt());
    }
    @Test
    void searchCarsByBrand_ShouldReturnMatchingCars() {
        Car car1 = new Car(1, "Toyota", "Camry", 2.5, 2021, 30000);
        when(carRepository.findByBrand("Toyota")).thenReturn(List.of(car1));

        List<CarDTO> result = carService.searchCarsByBrand("Toyota");

        System.out.println("Original Car: " + car1.getBrand());
        System.out.println("Converted CarDTO: " + result.get(0).getBrand());

        assertEquals(1, result.size(), "The result size should be 1.");
        assertEquals("Toyota", result.get(0).getBrand(), "The brand of the car should be Toyota.");
        verify(carRepository, times(1)).findByBrand("Toyota");
    }
}
