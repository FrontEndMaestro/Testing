package models;

import models.Location;
import models.Transport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TravelTimeIntegrationTest {

    private Transport transportAB;
    private Transport transportAC;
    private Transport transportBC;
    private Transport transportAA;

    @BeforeEach
    void setUp() {
        transportAB = new Transport(Location.A, Location.B);
        transportAC = new Transport(Location.A, Location.C);
        transportBC = new Transport(Location.B, Location.C);
        transportAA = new Transport(Location.A, Location.A);
    }

    @Test
    void testConsistentTravelTimes() {
        // Test travel time for A <-> B
        assertEquals(2.0, transportAB.getTravelTime(), 0.001);
        Transport transportBA = new Transport(Location.B, Location.A);
        assertEquals(2.0, transportBA.getTravelTime(), 0.001);

        // Test travel time for A <-> C
        assertEquals(4.0, transportAC.getTravelTime(), 0.001);
        Transport transportCA = new Transport(Location.C, Location.A);
        assertEquals(4.0, transportCA.getTravelTime(), 0.001);

        // Test travel time for B <-> C
        assertEquals(3.0, transportBC.getTravelTime(), 0.001);
        Transport transportCB = new Transport(Location.C, Location.B);
        assertEquals(3.0, transportCB.getTravelTime(), 0.001);

        // Test travel time for same location
        assertEquals(1.0, transportAA.getTravelTime(), 0.001);
    }

    @Test
    void testMultipleTransportsConsistency() {
        // Perform multiple transports and verify consistency
        for (int i = 0; i < 10; i++) {
            Transport tempTransport = new Transport(Location.A, Location.B);
            assertEquals(2.0, tempTransport.getTravelTime(), 0.001);

            tempTransport = new Transport(Location.B, Location.C);
            assertEquals(3.0, tempTransport.getTravelTime(), 0.001);

            tempTransport = new Transport(Location.C, Location.A);
            assertEquals(4.0, tempTransport.getTravelTime(), 0.001);

            tempTransport = new Transport(Location.A, Location.A);
            assertEquals(1.0, tempTransport.getTravelTime(), 0.001);
        }
    }
}