package com.commsignia.example.vehicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commsignia.example.vehicles.models.Vehicle;
import com.commsignia.example.vehicles.models.VehiclesResponseDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private WebSocketService webSocketService;

    private Map<String, Vehicle> registeredVehicles = new HashMap<>();

    public VehiclesResponseDTO queryVehiclesInCircle(double latitude, double longitude, double radius) {
        List<Vehicle> vehicles = registeredVehicles.values().stream()
                .filter(v -> Utils.calculateDistance(latitude, longitude, v.getLatitude(), v.getLongitude()) < radius)
                .collect(Collectors.toList());
        return new VehiclesResponseDTO(vehicles);
    }

    public Vehicle registerVehicle() {
        String vehicleId = generateUniqueId();
        Vehicle vehicle = new Vehicle(vehicleId);
        registeredVehicles.put(vehicleId, vehicle);
        return vehicle;
    }

    public void updateVehiclePosition(String id, double latitude, double longitude) {
        // Assuming the vehicle with the given ID exists
        if (registeredVehicles.containsKey(id)) {
            Vehicle vehicle = registeredVehicles.get(id);
            vehicle.setLatitude(latitude);
            vehicle.setLongitude(longitude);
            webSocketService.sendToTopic("vehicles/" + id + "/pos", vehicle);
        }
    }

    public void createNotification(String id, String message) {
        if (registeredVehicles.containsKey(id)) {
            webSocketService.sendToTopic("vehicles/" + id + "/not", message);
        }
    }

    private String generateUniqueId() {
        return "V" + System.currentTimeMillis(); // Using timestamp as ID
    }

}