// File: test/UtilsTest.java
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import models.Alpha;
import models.Beta;
import models.Gamma;
import models.Historic;
import models.Location;
import models.Recycling;
import models.Transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Utils.Utils;

public class UtilsTest {

    private Historic historic;
    private List<Recycling> recyclingCentres;

    @BeforeEach
    public void setUp() {
        // Initialize a Historic site with 1500.0 initial waste
        historic = new Historic(Location.A, 1500.0);

        // Initialize Recycling Centres
        recyclingCentres = new ArrayList<>();
        recyclingCentres.add(new Alpha(Location.B, 5));  // Viable
        recyclingCentres.add(new Beta(Location.C, 3));   // Not viable due to travel time from A to C = 4.0
        recyclingCentres.add(new Gamma(Location.B, 2));  // Viable
        recyclingCentres.add(new Gamma(Location.C, 4));  // Not viable
    }

    @Test
    public void testFindViableCentres() {
        List<Recycling> viableCentres = Utils.findViableCentres(new Historic(Location.A, 1500.0), new ArrayList<>(recyclingCentres));

        // Expected: Alpha(Location.B, 5) and Gamma(Location.B, 2)
        assertEquals(2, viableCentres.size(), "There should be 2 viable recycling centres");

        assertTrue(viableCentres.stream().anyMatch(c -> c.getGeneration().equals("Alpha") && c.getLocation() == Location.B));
        assertTrue(viableCentres.stream().anyMatch(c -> c.getGeneration().equals("Gamma") && c.getLocation() == Location.B));
    }

    @Test
    public void testFindViableCentresNoMetallicWaste() {
        // Set metallic waste to 0
        historic.setMetallic(0.0);
        List<Recycling> viableCentres = Utils.findViableCentres(historic, new ArrayList<>(recyclingCentres));

        // Expected: Only Alpha(Location.B, 5)
        assertEquals(1, viableCentres.size(), "There should be 1 viable recycling centre");
        assertTrue(viableCentres.stream().anyMatch(c -> c.getGeneration().equals("Alpha") && c.getLocation() == Location.B));
    }

    @Test
    public void testFindOptimalCentre() {
        List<Recycling> viableCentres = Utils.findViableCentres(historic, new ArrayList<>(recyclingCentres));
        Recycling optimalCentre = Utils.findOptimalCentre(historic, viableCentres);

        // Among viable centres: Alpha(Location.B, 5) and Gamma(Location.B, 2)
        // Nearest is both at Location.B (travel time 2.0)
        // Youngest is Gamma(Location.B, 2)
        // Highest generation between Alpha and Gamma is Gamma

        assertNotNull(optimalCentre, "Optimal centre should not be null");
        assertEquals("Gamma", optimalCentre.getGeneration(), "Optimal centre should be Gamma");
        assertEquals(Location.B, optimalCentre.getLocation(), "Optimal centre location should be B");
        assertEquals(2, optimalCentre.getYearsActive(), "Optimal centre years active should be 2");
    }

    @Test
    public void testFindNearestCentres() {
        List<Recycling> viableCentres = Utils.findViableCentres(historic, new ArrayList<>(recyclingCentres));
        List<Recycling> nearestCentres = Utils.findNearestCentres(historic, viableCentres);

        // Both viable centres are at Location.B with travel time 2.0
        assertEquals(2, nearestCentres.size(), "Both centres are nearest with travel time 2.0");
    }

    @Test
    public void testFindHighestGenerations() {
        List<Recycling> viableCentres = Utils.findViableCentres(historic, new ArrayList<>(recyclingCentres));
        List<Recycling> highestGenerations = Utils.findHighestGenerations(viableCentres);

        // Highest generation is Gamma
        assertEquals(1, highestGenerations.size(), "Only Gamma should be the highest generation");
        assertEquals("Gamma", highestGenerations.get(0).getGeneration(), "Generation should be Gamma");
    }

    @Test
    public void testCompareGenerations() {
        assertTrue(Utils.compareGenerations("Beta", "Alpha") > 0, "Beta should be greater than Alpha");
        assertTrue(Utils.compareGenerations("Gamma", "Beta") > 0, "Gamma should be greater than Beta");
        assertTrue(Utils.compareGenerations("Alpha", "Gamma") < 0, "Alpha should be less than Gamma");
        assertEquals(0, Utils.compareGenerations("Alpha", "Alpha"), "Alpha should be equal to Alpha");
    }

    @Test
    public void testFindLeastYearsActive() {
        List<Recycling> centres = new ArrayList<>();
        centres.add(new Alpha(Location.A, 5));
        centres.add(new Beta(Location.B, 2));
        centres.add(new Gamma(Location.C, 2));
        centres.add(new Gamma(Location.A, 3));

        List<Recycling> leastYears = Utils.findLeastYearsActive(centres);

        // Expected: Beta(Location.B, 2) and Gamma(Location.C, 2)
        assertEquals(2, leastYears.size(), "There should be 2 centres with least years active");
        assertTrue(leastYears.stream().anyMatch(c -> c.getLocation() == Location.B && c.getYearsActive() == 2));
        assertTrue(leastYears.stream().anyMatch(c -> c.getLocation() == Location.C && c.getYearsActive() == 2));
    }

    @Test
    public void testCalculateProcessDuration() {
        Recycling gamma = new Gamma(Location.B, 2);
        // Assuming plasticGlass = 450.0, paper = 750.0, metallic = 300.0
        double processDuration = Utils.calculateProcessDuration(historic, gamma);
        // Time = plasticGlass / 1.5 + paper / 2.0 + metallic / 3.0
        // = 450 / 1.5 + 750 / 2.0 + 300 / 3.0 = 300 + 375 + 100 = 775.0
        assertEquals(775.0, processDuration, 0.001, "Process duration should be 775.0 hours");
    }

    @Test
    public void testCalculateTravelDuration() {
        Recycling alpha = new Alpha(Location.B, 5);
        // Assuming TRANSPORT_CAPACITY = 20.0
        // Initial waste = 1500.0, metallic = 300.0, plasticGlass = 450.0, paper = 750.0

        // Adjust the Historic site for a manageable test scenario
        historic.setRemainingWaste(60.0); // Total waste: 60.0
        historic.setMetallic(20.0);
        historic.setPlasticGlass(20.0);
        historic.setPaper(20.0);

        double expectedTravelTime = 2.0 * 3; // 3 trips (60 / 20 = 3)
        double actualTravelTime = Utils.calculateTravelDuration(historic, alpha);

        assertEquals(expectedTravelTime, actualTravelTime, 0.001, "Travel duration should be 6.0 hours");
    }
}
