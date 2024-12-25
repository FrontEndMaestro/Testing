// File: test/models/AlphaTest.java
package models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AlphaTest {

    @Test
    public void testGetGeneration() {
        Location location = Location.A;
        Alpha alpha = new Alpha(location, 5);
        assertEquals("Alpha", alpha.getGeneration(), "Generation should be Alpha");
    }

    @Test
    public void testGetRates() {
        Location location = Location.B;
        Alpha alpha = new Alpha(location, 3);
        List<Double> expectedRates = List.of(1.0, 1.0, 1.0);
        assertEquals(expectedRates, alpha.getRates(), "Rates should be [1.0, 1.0, 1.0]");
    }
}
