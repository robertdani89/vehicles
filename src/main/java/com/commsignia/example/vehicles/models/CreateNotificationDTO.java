package com.commsignia.example.vehicles.models;

public class CreateNotificationDTO {
    private String vehicle_id;
    private String message;

    public String getVehicleId() {
        return vehicle_id;
    }

    public void setVehicleId(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
