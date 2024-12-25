package models;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WasteTransportIntegrationTest {

    private Alpha alphaSite;
    private Beta betaSite;
    private Gamma gammaSite;
    private Transport transportAB;
    private Transport transportBC;

    @BeforeEach
    void setUp() {
        // Initialize recycling sites
        alphaSite = new Alpha(Location.A, 5);
        betaSite = new Beta(Location.B, 3);
        gammaSite = new Gamma(Location.C, 10);

        // Initialize transports
        transportAB = new Transport(Location.A, Location.B);
        transportBC = new Transport(Location.B, Location.C);
    }

    @Test
    void testWasteGenerationAndTransport() {
        // Simulate waste generation at Alpha site
        double generatedWaste = 500.0; // Total waste
        Historic historicAlpha = new Historic(alphaSite.getLocation(), generatedWaste);
        

        // Verify initial waste split
        assertEquals(250.0, historicAlpha.getPaper(), 0.001);
        assertEquals(150.0, historicAlpha.getPlasticGlass(), 0.001);
        assertEquals(100.0, historicAlpha.getMetallic(), 0.001);

        // Transport waste from Alpha to Beta
        transportAB.setPaperWaste(historicAlpha.getPaper());
        transportAB.setPlasticGlassWaste(historicAlpha.getPlasticGlass());
        transportAB.setMetallicWaste(historicAlpha.getMetallic());

        // Simulate transportation time
        double travelTimeAB = transportAB.getTravelTime();
        assertEquals(2.0, travelTimeAB, 0.001);

        // Update waste quantities after transport
        historicAlpha.setRemainingWaste(historicAlpha.getRemainingWaste() - transportAB.getTotalWaste());

        // Verify remaining waste at Alpha
        assertEquals(0.0, historicAlpha.getRemainingWaste(), 0.001);

        // Simulate waste reception at Beta
        Historic historicBeta = new Historic(betaSite.getLocation(), transportAB.getTotalWaste());

        // Verify waste split at Beta
        assertEquals(125.0, historicBeta.getPaper(), 0.001);
        assertEquals(75.0, historicBeta.getPlasticGlass(), 0.001);
        assertEquals(50.0, historicBeta.getMetallic(), 0.001);

        // Further transport from Beta to C
        transportBC.setPaperWaste(historicBeta.getPaper());
        transportBC.setPlasticGlassWaste(historicBeta.getPlasticGlass());
        transportBC.setMetallicWaste(historicBeta.getMetallic());

        // Simulate transportation time
        double travelTimeBC = transportBC.getTravelTime();
        assertEquals(3.0, travelTimeBC, 0.001);

        // Update waste quantities after transport
        historicBeta.setRemainingWaste(historicBeta.getRemainingWaste() - transportBC.getTotalWaste());

        // Verify remaining waste at Beta
        assertEquals(0.0, historicBeta.getRemainingWaste(), 0.001);

        // Simulate waste reception at Gamma
        Historic historicGamma = new Historic(gammaSite.getLocation(), transportBC.getTotalWaste());

        // Verify waste split at Gamma
        assertEquals(250.0, historicGamma.getPaper(), 0.001); // 50% of 500
        assertEquals(150.0, historicGamma.getPlasticGlass(), 0.001); // 30% of 500
        assertEquals(100.0, historicGamma.getMetallic(), 0.001); // 20% of 500
    }
}