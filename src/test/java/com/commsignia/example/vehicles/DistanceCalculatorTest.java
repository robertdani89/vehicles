package com.commsignia.example.vehicles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DistanceCalculatorTest {
    @Test
    public void testDistanceBetweenSameCoordinates() {
        double lat = 40.7128; // Latitude
        double lon = -74.0060; // Longitude

        double distance = Utils.calculateDistance(lat, lon, lat, lon);

        // Expected distance between the same coordinates should be 0
        assertEquals(0, distance, 0);
    }

    @Test
    public void testDistanceBetweenOppositePoints() {
        double lat1 = 53.32055555555556;
        double lon1 = -1.7297222222222221;
        double lat2 = 53.31861111111111;
        double lon2 = -1.6997222222222223;

        double distance = Utils.calculateDistance(lat1, lon1, lat2, lon2);

        assertEquals(2.004367838271690, distance, 10); // Allow a small margin for error
    }

    @Test
    public void testDistanceBetweenClosePoints() {
        double lat1 = 40.7128; // Latitude of New York City
        double lon1 = -74.0060; // Longitude of New York City
        double lat2 = 40.7536; // Latitude of Jersey City
        double lon2 = -74.0377; // Longitude of Jersey City

        double distance = Utils.calculateDistance(lat1, lon1, lat2, lon2);

        // Expected distance between New York City and Jersey City (approximate)
        assertEquals(5.2, distance, 0.1); // Allow a small margin for error
    }
}
