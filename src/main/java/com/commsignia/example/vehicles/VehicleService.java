package com.commsignia.example.vehicles;

import org.springframework.stereotype.Service;

import com.commsignia.example.vehicles.models.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class VehicleService {

    private Map<String, Vehicle> registeredVehicles = new HashMap<>();

    public ArrayList<Vehicle> queryVehiclesInCircle(double latitude, double longitude, double radius) {
        // This is a placeholder implementation. Replace it with your actual logic.
        // For now, we'll return an empty list.
        return new ArrayList<Vehicle>(registeredVehicles.values());
    }

    public String registerVehicle() {
        String vehicleId = generateUniqueId();
        Vehicle vehicle = new Vehicle(vehicleId);
        registeredVehicles.put(vehicleId, vehicle);
        return vehicleId;
    }

    public void updateVehiclePosition(String id, double latitude, double longitude) {
        // Assuming the vehicle with the given ID exists
        if (registeredVehicles.containsKey(id)) {
            Vehicle vehicle = registeredVehicles.get(id);
            vehicle.setLatitude(latitude);
            vehicle.setLongitude(longitude);
        }
    }

    private String generateUniqueId() {
        // This is a simplified implementation for demonstration purposes
        // In production, you should use a more robust method to generate unique IDs
        return "V" + System.currentTimeMillis(); // Using timestamp as ID
    }
}