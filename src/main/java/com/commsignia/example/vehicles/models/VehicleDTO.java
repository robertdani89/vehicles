package com.commsignia.example.vehicles.models;

public class VehicleDTO {
    private String vehicleId;
    private double longitude;
    private double latitude;

    // Constructors
    public VehicleDTO() {
    }

    public VehicleDTO(String vehicleId, double longitude, double latitude) {
        this.vehicleId = vehicleId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters and setters
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "vehicleId='" + vehicleId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}