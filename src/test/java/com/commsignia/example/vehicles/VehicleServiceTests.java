package com.commsignia.example.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.commsignia.example.vehicles.models.Vehicle;
import com.commsignia.example.vehicles.models.VehiclesResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VehicleServiceTests {

    @Mock
    private Map<String, Vehicle> registeredVehicles;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQueryVehiclesInCircle() {
        // Mock data
        double latitude = 0.0;
        double longitude = 0.0;
        double radius = 10.0;
        List<Vehicle> expectedVehicles = new ArrayList<>();

        // Mock behavior
        when(registeredVehicles.values()).thenReturn(expectedVehicles);

        // Invoke the method
        VehiclesResponseDTO result = vehicleService.queryVehiclesInCircle(latitude, longitude, radius);

        // Verify behavior
        verify(registeredVehicles).values();
        assertEquals(expectedVehicles, result.getVehicles());
    }

    @Test
    public void testRegisterVehicle() {
        // Invoke the method
        vehicleService.registerVehicle();

        // Verify behavior
        verify(registeredVehicles).put(anyString(), any(Vehicle.class));
    }

    @Test
    public void testUpdateVehiclePosition() {
        // Mock data
        String vehicleId = "test123";
        double latitude = 0.0;
        double longitude = 0.0;

        // Mock behavior
        Vehicle vehicle = mock(Vehicle.class);
        when(registeredVehicles.containsKey(vehicleId)).thenReturn(true);
        when(registeredVehicles.get(vehicleId)).thenReturn(vehicle);

        // Invoke the method
        vehicleService.updateVehiclePosition(vehicleId, latitude, longitude);

        // Verify behavior
        verify(vehicle).setLatitude(latitude);
        verify(vehicle).setLongitude(longitude);
    }
}