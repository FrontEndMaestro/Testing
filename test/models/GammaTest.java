// File: test/models/GammaTest.java
package models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class GammaTest {

    @Test
    public void testGetGeneration() {
        Location location = Location.C;
        Gamma gamma = new Gamma(location, 6);
        assertEquals("Gamma", gamma.getGeneration(), "Generation should be Gamma");
    }

    @Test
    public void testGetRates() {
        Location location = Location.A;
        Gamma gamma = new Gamma(location, 1);
        List<Double> expectedRates = List.of(1.5, 2.0, 3.0);
        assertEquals(expectedRates, gamma.getRates(), "Rates should be [1.5, 2.0, 3.0]");
    }
}
