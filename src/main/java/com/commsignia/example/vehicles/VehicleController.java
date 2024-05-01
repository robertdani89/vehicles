package com.commsignia.example.vehicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.commsignia.example.vehicles.models.Vehicle;

import java.util.List;

@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public List<Vehicle> queryVehiclesInCircle(@RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius) {
        return vehicleService.queryVehiclesInCircle(latitude, longitude, radius);
    }

    @PostMapping("/vehicles")
    public String registerVehicle() {
        return vehicleService.registerVehicle();
    }

    @PostMapping("/vehicle/{id}")
    public void updateVehiclePosition(@PathVariable String id,
            @RequestParam double latitude,
            @RequestParam double longitude) {
        vehicleService.updateVehiclePosition(id, latitude, longitude);
    }

    @PostMapping("/notifications")
    public void createNotification(@RequestParam String vehicle_id,
            @RequestParam String message) {
        // Your logic to handle notifications
    }

    @PutMapping("/subscribe")
    public void subscribeToVehicle(@SessionId String sessionId, @RequestParam String vehicleId) {
        vehicleService.subscribeToVehicle(sessionId, vehicleId);
    }

    @PutMapping("/unsubscribe")
    public void unsubscribeFromVehicle(@SessionId String sessionId, @RequestParam String vehicleId) {
        vehicleService.unsubscribeFromVehicle(sessionId, vehicleId);
    }

}