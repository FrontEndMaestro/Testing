// File: test/models/TransportTest.java
package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TransportTest {

    @Test
    public void testConstructorAndGetters() {
        Transport transport = new Transport(Location.A, Location.B);
        assertEquals(Location.A, transport.getStart(), "Start location should be A");
        assertEquals(Location.B, transport.getEnd(), "End location should be B");
        assertEquals(0.0, transport.getPaperWaste(), 0.001, "Initial paper waste should be 0.0");
        assertEquals(0.0, transport.getPlasticGlassWaste(), 0.001, "Initial plastic/glass waste should be 0.0");
        assertEquals(0.0, transport.getMetallicWaste(), 0.001, "Initial metallic waste should be 0.0");
    }

    @Test
    public void testSettersAndGetters() {
        Transport transport = new Transport(Location.B, Location.C);
        transport.setPaperWaste(100.0);
        transport.setPlasticGlassWaste(50.0);
        transport.setMetallicWaste(25.0);

        assertEquals(100.0, transport.getPaperWaste(), 0.001, "Paper waste should be 100.0");
        assertEquals(50.0, transport.getPlasticGlassWaste(), 0.001, "Plastic/Glass waste should be 50.0");
        assertEquals(25.0, transport.getMetallicWaste(), 0.001, "Metallic waste should be 25.0");
    }

    @Test
    public void testGetTotalWaste() {
        Transport transport = new Transport(Location.C, Location.A);
        transport.setPaperWaste(200.0);
        transport.setPlasticGlassWaste(100.0);
        transport.setMetallicWaste(50.0);

        assertEquals(350.0, transport.getTotalWaste(), 0.001, "Total waste should be 350.0");
    }

    @Test
    public void testGetTravelTime() {
        Transport transportAB = new Transport(Location.A, Location.B);
        assertEquals(2.0, transportAB.getTravelTime(), 0.001, "Travel time from A to B should be 2.0");

        Transport transportAC = new Transport(Location.A, Location.C);
        assertEquals(4.0, transportAC.getTravelTime(), 0.001, "Travel time from A to C should be 4.0");

        Transport transportBC = new Transport(Location.B, Location.C);
        assertEquals(3.0, transportBC.getTravelTime(), 0.001, "Travel time from B to C should be 3.0");

        Transport transportAA = new Transport(Location.A, Location.A);
        assertEquals(0.0, transportAA.getTravelTime(), 0.001, "Travel time within the same location should be 0.0");
    }
}
