package com.commsignia.example.vehicles.models;

import java.util.List;

public class VehiclesResponseDTO {
    private List<Vehicle> vehicles;

    public VehiclesResponseDTO() {
    }

    public VehiclesResponseDTO(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> value) {
        this.vehicles = value;
    }
}