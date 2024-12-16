package kg.asiamotors.demo.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import kg.asiamotors.demo.dto.FuelTypeDTO;
import kg.asiamotors.demo.service.FuelTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FuelTypeApiControllerTest {

    private FuelTypeApiController fuelTypeApiController;

    @Mock
    private FuelTypeService fuelTypeService;

    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private Counter fuelTypeRequestCounter;

    @Mock
    private Timer fuelTypeRequestTimer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(meterRegistry.counter("fuel_type_requests_total")).thenReturn(fuelTypeRequestCounter);
        when(meterRegistry.timer("fuel_type_requests_duration")).thenReturn(fuelTypeRequestTimer);

        fuelTypeApiController = new FuelTypeApiController(fuelTypeService, meterRegistry);
    }

    @Test
    void testGetAllFuelTypes() {
        List<FuelTypeDTO> fuelTypes = Arrays.asList(
                new FuelTypeDTO("Бензин"),
                new FuelTypeDTO("Дизель")
        );
        when(fuelTypeService.getAllFuelTypes()).thenReturn(fuelTypes);

        List<FuelTypeDTO> result = fuelTypeApiController.getAllFuelTypes();

        assertEquals(2, result.size());
        assertEquals("Бензин", result.get(0).getName());
        verify(fuelTypeRequestCounter, times(1)).increment();
        verify(fuelTypeRequestTimer, times(1)).record(anyLong(), eq(TimeUnit.NANOSECONDS));
        verify(fuelTypeService, times(1)).getAllFuelTypes();
    }

    @Test
    void testGetFuelTypeById() {
        FuelTypeDTO fuelTypeDTO = new FuelTypeDTO("Бензин");
        when(fuelTypeService.getFuelTypeById(1)).thenReturn(ResponseEntity.ok(fuelTypeDTO));

        ResponseEntity<FuelTypeDTO> result = fuelTypeApiController.getFuelTypeById(1);

        assertEquals("Бензин", result.getBody().getName());
        verify(fuelTypeRequestCounter, times(1)).increment();
        verify(fuelTypeRequestTimer, times(1)).record(anyLong(), eq(TimeUnit.NANOSECONDS));
        verify(fuelTypeService, times(1)).getFuelTypeById(1);
    }

    @Test
    void testCreateFuelType() {
        FuelTypeDTO fuelTypeDTO = new FuelTypeDTO("Бензин");
        when(fuelTypeService.createFuelType(fuelTypeDTO)).thenReturn(ResponseEntity.ok(fuelTypeDTO));

        ResponseEntity<FuelTypeDTO> result = fuelTypeApiController.createFuelType(fuelTypeDTO);

        assertEquals("Бензин", result.getBody().getName());
        verify(fuelTypeRequestCounter, times(1)).increment();
        verify(fuelTypeRequestTimer, times(1)).record(anyLong(), eq(TimeUnit.NANOSECONDS));
        verify(fuelTypeService, times(1)).createFuelType(fuelTypeDTO);
    }

    @Test
    void testUpdateFuelType() {
        FuelTypeDTO fuelTypeDTO = new FuelTypeDTO("Дизель");
        when(fuelTypeService.updateFuelType(1, fuelTypeDTO)).thenReturn(ResponseEntity.ok(fuelTypeDTO));

        ResponseEntity<FuelTypeDTO> result = fuelTypeApiController.updateFuelType(1, fuelTypeDTO);

        assertEquals("Дизель", result.getBody().getName());
        verify(fuelTypeRequestCounter, times(1)).increment();
        verify(fuelTypeRequestTimer, times(1)).record(anyLong(), eq(TimeUnit.NANOSECONDS));
        verify(fuelTypeService, times(1)).updateFuelType(1, fuelTypeDTO);
    }

    @Test
    void testDeleteFuelType() {
        when(fuelTypeService.deleteFuelType(1)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<Void> result = fuelTypeApiController.deleteFuelType(1);

        assertEquals(200, result.getStatusCodeValue());
        verify(fuelTypeRequestCounter, times(1)).increment();
        verify(fuelTypeRequestTimer, times(1)).record(anyLong(), eq(TimeUnit.NANOSECONDS));
        verify(fuelTypeService, times(1)).deleteFuelType(1);
    }

    @Test
    void testSearchFuelTypesByName() {
        List<FuelTypeDTO> fuelTypes = Arrays.asList(new FuelTypeDTO("Бензин"));
        when(fuelTypeService.searchFuelTypesByName("Бензин")).thenReturn(fuelTypes);

        List<FuelTypeDTO> result = fuelTypeApiController.searchFuelTypesByName("Бензин");

        assertEquals(1, result.size());
        assertEquals("Бензин", result.get(0).getName());
        verify(fuelTypeRequestCounter, times(1)).increment();
        verify(fuelTypeRequestTimer, times(1)).record(anyLong(), eq(TimeUnit.NANOSECONDS));
        verify(fuelTypeService, times(1)).searchFuelTypesByName("Бензин");
    }
}
