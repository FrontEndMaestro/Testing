// File: test/ScenarioConfigurationTest.java
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import models.Alpha;
import models.Historic;
import models.Location;
import models.Recycling;


import org.junit.jupiter.api.Test;

public class ScenarioConfigurationTest {

    @Test
    public void testConstructorAndGetters() {
        Historic historic = new Historic(Location.A, 1500.0);
        List<Recycling> recycling = new ArrayList<>();
        recycling.add(new Alpha(Location.B, 5));

        ScenarioConfiguration config = new ScenarioConfiguration(historic, recycling);

        assertEquals(historic, config.getHistoric(), "Historic should match the one provided");
        assertEquals(recycling, config.getRecycling(), "Recycling list should match the one provided");
    }

    @Test
    public void testSetters() {
        ScenarioConfiguration config = new ScenarioConfiguration();

        Historic historic = new Historic(Location.B, 1000.0);
        config.setHistoric(historic);
        assertEquals(historic, config.getHistoric(), "Historic should be updated");

        Recycling recycling = new Alpha(Location.C, 3);
        config.addRecycling(recycling);
        assertEquals(1, config.getRecycling().size(), "Recycling list should have one item");
        assertEquals(recycling, config.getRecycling().get(0), "Recycling center should match the one added");
    }
}
