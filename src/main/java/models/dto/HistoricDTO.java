package models.dto;

public class HistoricDTO {
    private String location;
    private double initialWaste;

    // Getters and Setters

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getInitialWaste() {
        return initialWaste;
    }

    public void setInitialWaste(double initialWaste) {
        this.initialWaste = initialWaste;
    }
}
