package com.commsignia.example.vehicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.commsignia.example.vehicles.models.CreateNotificationDTO;
import com.commsignia.example.vehicles.models.Vehicle;
import com.commsignia.example.vehicles.models.VehicleUpdateDTO;
import com.commsignia.example.vehicles.models.VehiclesResponseDTO;

@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public VehiclesResponseDTO queryVehiclesInCircle(@RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius) {
        return vehicleService.queryVehiclesInCircle(latitude, longitude, radius);
    }

    @PostMapping("/vehicles")
    public Vehicle registerVehicle() {
        return vehicleService.registerVehicle();
    }

    @PostMapping("/vehicle/{id}")
    public void updateVehiclePosition(@PathVariable String id, @RequestBody VehicleUpdateDTO body) {
        vehicleService.updateVehiclePosition(id, body.getLatitude(), body.getLongitude());
    }

    @PostMapping("/notifications")
    public void createNotification(@RequestBody CreateNotificationDTO body) {
        vehicleService.createNotification(body.getVehicleId(), body.getMessage());
    }
}
