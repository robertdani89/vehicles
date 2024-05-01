package com.commsignia.example.vehicles;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class VehicleSubscriptionManager {

    // HashMap to store vehicle IDs as keys and corresponding session IDs as values
    private Map<String, Set<String>> subscriptions = new HashMap<>();

    // Method to subscribe a session to a vehicle
    public void subscribe(String sessionId, String vehicleId) {
        // Get or create a Set of session IDs associated with the vehicle ID
        subscriptions.computeIfAbsent(vehicleId, key -> new HashSet<>()).add(sessionId);
    }

    // Method to unsubscribe a session from a vehicle
    public void unsubscribe(String sessionId, String vehicleId) {
        // Get the Set of session IDs associated with the vehicle ID
        Set<String> sessionIds = subscriptions.get(vehicleId);
        // If the Set exists, remove the session ID from it
        if (sessionIds != null) {
            sessionIds.remove(sessionId);
            // If the Set becomes empty after removal, remove the entry from the map
            if (sessionIds.isEmpty()) {
                subscriptions.remove(vehicleId);
            }
        }
    }

    // Method to get all session IDs subscribed to a vehicle
    public Set<String> getClientsByVehicleId(String vehicleId) {
        // Return the Set of session IDs associated with the vehicle ID, or an empty Set
        // if no subscriptions exist
        return subscriptions.getOrDefault(vehicleId, Collections.emptySet());
    }
}