package com.commsignia.example.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleSubscriptionManagerTests {

    private VehicleSubscriptionManager subscriptionManager;

    @BeforeEach
    public void setup() {
        subscriptionManager = new VehicleSubscriptionManager();
    }

    @Test
    public void testSubscribeAndUnsubscribe() {
        String sessionId = "session123";
        String vehicleId = "vehicle456";

        // Subscribe session to vehicle
        subscriptionManager.subscribe(sessionId, vehicleId);

        // Get clients by vehicle ID and verify session is subscribed
        Set<String> clients = subscriptionManager.getClientsByVehicleId(vehicleId);
        assertTrue(clients.contains(sessionId));

        // Unsubscribe session from vehicle
        subscriptionManager.unsubscribe(sessionId, vehicleId);

        // Get clients by vehicle ID and verify session is unsubscribed
        clients = subscriptionManager.getClientsByVehicleId(vehicleId);
        assertTrue(clients.isEmpty());
    }

    @Test
    public void testGetClientsByVehicleId() {
        String vehicleId = "vehicle789";

        // Subscribe sessions to vehicle
        subscriptionManager.subscribe("session1", vehicleId);
        subscriptionManager.subscribe("session2", vehicleId);
        subscriptionManager.subscribe("session3", vehicleId);

        // Get clients by vehicle ID and verify all sessions are subscribed
        Set<String> clients = subscriptionManager.getClientsByVehicleId(vehicleId);
        assertEquals(3, clients.size());
        assertTrue(clients.contains("session1"));
        assertTrue(clients.contains("session2"));
        assertTrue(clients.contains("session3"));
    }
}