package models;

import models.Historic;
import models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricProcessingIntegrationTest {

    private Historic historicHighWaste;
    private Historic historicLowWaste;

    @BeforeEach
    void setUp() {
        // Initialize Historic sites with different initial wastes
        historicHighWaste = new Historic(Location.C, 2000.0); // Above metallic threshold
        historicLowWaste = new Historic(Location.B, 1000.0);  // Below metallic threshold
    }

    @Test
    void testHighWasteEstimation() {
        // Verify waste split for high waste
        assertEquals(2000.0 * 0.5, historicHighWaste.getPaper(), 0.001);
        assertEquals(2000.0 * 0.3, historicHighWaste.getPlasticGlass(), 0.001);
        assertEquals(2000.0 * 0.2, historicHighWaste.getMetallic(), 0.001);
    }

    @Test
    void testLowWasteEstimation() {
        // Verify waste split for low waste
        assertEquals(1000.0 * 0.5, historicLowWaste.getPaper(), 0.001);
        assertEquals(1000.0 * 0.5, historicLowWaste.getPlasticGlass(), 0.001);
        assertEquals(0.0, historicLowWaste.getMetallic(), 0.001);
    }

    @Test
    void testWasteUpdateAfterProcessing() {
        // Update waste at high waste site
        historicHighWaste.setRemainingWaste(1800.0);
        historicHighWaste.setPaper(900.0);
        historicHighWaste.setPlasticGlass(540.0);
        historicHighWaste.setMetallic(360.0);

        // Verify updated waste
        assertEquals(1800.0, historicHighWaste.getRemainingWaste(), 0.001);
        assertEquals(900.0, historicHighWaste.getPaper(), 0.001);
        assertEquals(540.0, historicHighWaste.getPlasticGlass(), 0.001);
        assertEquals(360.0, historicHighWaste.getMetallic(), 0.001);

        // Update waste at low waste site
        historicLowWaste.setRemainingWaste(800.0);
        historicLowWaste.setPaper(400.0);
        historicLowWaste.setPlasticGlass(400.0);
        historicLowWaste.setMetallic(0.0);

        // Verify updated waste
        assertEquals(800.0, historicLowWaste.getRemainingWaste(), 0.001);
        assertEquals(400.0, historicLowWaste.getPaper(), 0.001);
        assertEquals(400.0, historicLowWaste.getPlasticGlass(), 0.001);
        assertEquals(0.0, historicLowWaste.getMetallic(), 0.001);
    }
}