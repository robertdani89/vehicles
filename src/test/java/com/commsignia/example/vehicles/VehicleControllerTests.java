package com.commsignia.example.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.commsignia.example.vehicles.models.Vehicle;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQueryVehiclesInCircle() throws Exception {
        // Define sample list of vehicles
        ArrayList<Vehicle> vehiclesInCircle = new ArrayList<>();
        vehiclesInCircle.add(new Vehicle("1"));
        vehiclesInCircle.add(new Vehicle("2"));

        // Mock the behavior of the service method
        when(vehicleService.queryVehiclesInCircle(anyDouble(), anyDouble(),
                anyDouble()))
                .thenReturn(vehiclesInCircle);

        MockitoAnnotations.openMocks(this);

        // Perform the request and verify the response
        mockMvc.perform(get("/vehicles")
                .param("latitude", "0.0")
                .param("longitude", "0.0")
                .param("radius", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @Test
    public void testRegisterVehicle() throws Exception {
        // Mock the behavior of the service method
        String expectedId = "generated_id";
        when(vehicleService.registerVehicle()).thenReturn(expectedId);

        // Perform the request and verify the response
        mockMvc.perform(post("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedId));
    }

    @Test
    public void testUpdateVehiclePosition() throws Exception {
        // Mock data
        String vehicleId = "123";
        double latitude = 0.0;
        double longitude = 0.0;

        // Perform the request
        mockMvc.perform(post("/vehicle/{id}", vehicleId)
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude)))
                .andExpect(status().isOk());

        // Verify that the service method is called with the correct arguments
        verify(vehicleService).updateVehiclePosition(vehicleId, latitude, longitude);
    }

    @Test
    public void testCreateNotification() throws Exception {
        // Mock data
        String vehicleId = "test_vehicle_id";
        String message = "Test notification message";

        // Perform the request
        mockMvc.perform(post("/notifications")
                .param("vehicle_id", vehicleId)
                .param("message", message))
                .andExpect(status().isOk());
    }
}