// File: test/models/HistoricTest.java
package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HistoricTest {

    @Test
    public void testConstructorAndGetters() {
        Location location = Location.A;
        double initialWaste = 1500.0;
        Historic historic = new Historic(location, initialWaste);

        assertEquals(location, historic.getLocation(), "Location should be A");
        assertEquals(initialWaste, historic.getRemainingWaste(), 0.001, "Remaining waste should match initial waste");
        assertEquals(750.0, historic.getPaper(), 0.001, "Paper waste should be 50% of initial waste");
        assertEquals(450.0, historic.getPlasticGlass(), 0.001, "Plastic/Glass waste should be 30% of initial waste");
        assertEquals(300.0, historic.getMetallic(), 0.001, "Metallic waste should be 20% of initial waste");
    }

    @Test
    public void testEstimateWasteSplitBelowThreshold() {
        Location location = Location.B;
        double initialWaste = 1000.0; // Below METALLIC_THRESH (1250.0)
        Historic historic = new Historic(location, initialWaste);

        assertEquals(500.0, historic.getPaper(), 0.001, "Paper waste should be 50% of initial waste");
        assertEquals(500.0, historic.getPlasticGlass(), 0.001, "Plastic/Glass waste should be 50% of initial waste");
        assertEquals(0.0, historic.getMetallic(), 0.001, "Metallic waste should be 0.0 when below threshold");
    }

    @Test
    public void testSetters() {
        Location location = Location.C;
        double initialWaste = 1300.0;
        Historic historic = new Historic(location, initialWaste);

        historic.setRemainingWaste(1200.0);
        historic.setPlasticGlass(400.0);
        historic.setPaper(600.0);
        historic.setMetallic(200.0);

        assertEquals(1200.0, historic.getRemainingWaste(), 0.001, "Remaining waste should be updated to 1200.0");
        assertEquals(400.0, historic.getPlasticGlass(), 0.001, "Plastic/Glass waste should be updated to 400.0");
        assertEquals(600.0, historic.getPaper(), 0.001, "Paper waste should be updated to 600.0");
        assertEquals(200.0, historic.getMetallic(), 0.001, "Metallic waste should be updated to 200.0");
    }
}
