package models;

import models.dto.HistoricDTO;
import models.dto.RecyclingDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import Utils.Utils;
@RestController
@RequestMapping("/api/scenarios")
public class ScenarioController {

    private Historic historic;
    private List<Recycling> recyclingCenters = new ArrayList<>();

    // Endpoint to create a Historic site
    @PostMapping("/historic")
    public ResponseEntity<String> createHistoric(@RequestBody HistoricDTO historicDTO) {
        try {
            Location location = Location.valueOf(historicDTO.getLocation().toUpperCase());
            historic = new Historic(location, historicDTO.getInitialWaste());
            return new ResponseEntity<>("Historic site created successfully.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid location provided.", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to add a Recycling center
    @PostMapping("/recycling")
    public ResponseEntity<String> addRecycling(@RequestBody RecyclingDTO recyclingDTO) {
        try {
            Location location = Location.valueOf(recyclingDTO.getLocation().toUpperCase());
            Recycling recycling;
            switch (recyclingDTO.getGeneration().toUpperCase()) {
                case "ALPHA":
                    recycling = new Alpha(location, recyclingDTO.getYearsActive());
                    break;
                case "BETA":
                    recycling = new Beta(location, recyclingDTO.getYearsActive());
                    break;
                case "GAMMA":
                    recycling = new Gamma(location, recyclingDTO.getYearsActive());
                    break;
                default:
                    return new ResponseEntity<>("Invalid generation type.", HttpStatus.BAD_REQUEST);
            }
            recyclingCenters.add(recycling);
            return new ResponseEntity<>("Recycling center added successfully.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid location provided.", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to run a scenario
    @PostMapping("/run")
    public ResponseEntity<String> runScenario() {
        if (historic == null || recyclingCenters.isEmpty()) {
            return new ResponseEntity<>("Historic site or Recycling centers not configured.", HttpStatus.BAD_REQUEST);
        }

        // Run the scenario using Utils
        List<Recycling> viableCentres = Utils.findViableCentres(historic, new ArrayList<>(recyclingCenters));
        if (viableCentres.isEmpty()) {
            return new ResponseEntity<>("No viable recycling centers found.", HttpStatus.OK);
        }

        Recycling optimalCentre = Utils.findOptimalCentre(historic, viableCentres);
        double travelDuration = Utils.calculateTravelDuration(historic, optimalCentre);
        double processDuration = Utils.calculateProcessDuration(historic, optimalCentre);

        String result = String.format("Optimal Recycling Center: %s at %s%nTravel Duration: %.2f hours%nProcess Duration: %.2f hours%nTotal Duration: %.2f hours",
                optimalCentre.getGeneration(),
                optimalCentre.getLocation(),
                travelDuration,
                processDuration,
                travelDuration + processDuration);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
