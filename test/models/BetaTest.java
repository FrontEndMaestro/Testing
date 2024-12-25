// File: test/models/BetaTest.java
package models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BetaTest {

    @Test
    public void testGetGeneration() {
        Location location = Location.B;
        Beta beta = new Beta(location, 4);
        assertEquals("Beta", beta.getGeneration(), "Generation should be Beta");
    }

    @Test
    public void testGetRates() {
        Location location = Location.C;
        Beta beta = new Beta(location, 2);
        List<Double> expectedRates = List.of(1.5, 1.5, 1.5);
        assertEquals(expectedRates, beta.getRates(), "Rates should be [1.5, 1.5, 1.5]");
    }
}
