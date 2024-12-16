package kg.asiamotors.demo.service;

import kg.asiamotors.demo.dto.CarModelDTO;
import kg.asiamotors.demo.exceptions.ResourceNotFoundException;
import kg.asiamotors.demo.models.CarModel;
import kg.asiamotors.demo.repository.CarModelRepository;
import kg.asiamotors.demo.models.Brand;
import kg.asiamotors.demo.models.Volume;
import kg.asiamotors.demo.models.Transmission;
import kg.asiamotors.demo.models.Drive;
import kg.asiamotors.demo.models.FuelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.List;

class CarModelServiceTest {
    @InjectMocks
    private CarModelService carModelService;

    @Mock
    private CarModelRepository carModelRepository;

    @Mock
    private BrandService brandService;

    @Mock
    private VolumeService volumeService;

    @Mock
    private TransmissionService transmissionService;

    @Mock
    private DriveService driveService;

    @Mock
    private FuelTypeService fuelTypeService;

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
        CarModel carModel = new CarModel();
        carModel.setName("Model S");

        Brand brand = new Brand();
        brand.setId(1);
        carModel.setBrand(brand);

        Volume volume = new Volume();
        volume.setId(1);
        carModel.setVolume(volume);

        Transmission transmission = new Transmission();
        transmission.setId(1);
        carModel.setTransmission(transmission);

        Drive drive = new Drive();
        drive.setId(1);
        carModel.setDrive(drive);

        FuelType fuelType = new FuelType();
        fuelType.setId(1);
        carModel.setFuelType(fuelType);

        when(carModelRepository.findAll()).thenReturn(List.of(carModel));

        var result = carModelService.getAllCarModels();

        assertEquals(1, result.size());
        assertEquals("Model S", result.get(0).getName());
        assertEquals(1, result.get(0).getBrandId());
    }

    @Test
    void testCreateCarModel() {
        CarModel carModel = new CarModel();
        carModel.setName("Model X");

        when(brandService.findById(anyInt())).thenReturn(new Brand());
        when(volumeService.findById(anyInt())).thenReturn(new Volume());
        when(transmissionService.findById(anyInt())).thenReturn(new Transmission());
        when(driveService.findEntityById(anyInt())).thenReturn(new Drive());
        when(fuelTypeService.findById(anyInt())).thenReturn(new FuelType());
        when(carModelRepository.save(any(CarModel.class))).thenReturn(carModel);

        CarModel result = carModelService.createCarModel(carModelDTO);

        assertNotNull(result);
        assertEquals("Model X", result.getName());
    }

    @Test
    void testUpdateCarModel() {
        CarModel existingCarModel = new CarModel();
        existingCarModel.setName("Old Model");

        CarModelDTO carModelDTO = new CarModelDTO();
        carModelDTO.setName("Old Model");
        carModelDTO.setBrandId(1);
        carModelDTO.setVolumeId(1);
        carModelDTO.setTransmissionId(1);
        carModelDTO.setDriveId(1);
        carModelDTO.setFuelTypeId(1);

        when(carModelRepository.findById(1)).thenReturn(Optional.of(existingCarModel));
        when(brandService.findById(anyInt())).thenReturn(new Brand());
        when(volumeService.findById(anyInt())).thenReturn(new Volume());
        when(transmissionService.findById(anyInt())).thenReturn(new Transmission());
        when(driveService.findEntityById(anyInt())).thenReturn(new Drive());
        when(fuelTypeService.findById(anyInt())).thenReturn(new FuelType());
        when(carModelRepository.save(any(CarModel.class))).thenReturn(existingCarModel);

        CarModel result = carModelService.updateCarModel(1, carModelDTO);

        assertNotNull(result);
        assertEquals("Old Model", result.getName());
    }

    @Test
    void testUpdateCarModelNotFound() {
        when(carModelRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> carModelService.updateCarModel(1, carModelDTO));
    }

    @Test
    void testDeleteCarModel() {
        when(carModelRepository.existsById(1)).thenReturn(true);

        boolean result = carModelService.deleteCarModel(1);

        assertTrue(result);
        verify(carModelRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteCarModelNotFound() {
        when(carModelRepository.existsById(1)).thenReturn(false);
        boolean result = carModelService.deleteCarModel(1);
        assertFalse(result);
    }
}
