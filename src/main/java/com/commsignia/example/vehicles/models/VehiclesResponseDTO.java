package com.commsignia.example.vehicles.models;

import java.util.List;

public class VehiclesResponseDTO {
    private List<Vehicle> vehicles;

    public VehiclesResponseDTO(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    // Getters and setters
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> value) {
        this.vehicles = value;
    }
}