package com.commsignia.example.vehicles.models;

public class Vehicle {
    private String id;
    private double latitude;
    private double longitude;

    public Vehicle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}