package edu.bsu.cs222.astraios.model;

public class VisibilityChecker {

    private final AstronomicalObject objectToCheck;
    private final Observation observation;

    public VisibilityChecker(AstronomicalObject objectToCheck, Observation observation) {
        this.objectToCheck = objectToCheck;
        this.observation = observation;
    }

    public boolean objectIsVisible() {
        Sun sun = new Sun();
        return !sun.sunInterferesWithViewing(observation)
                && objectToCheck.getAltAzAtObservation(observation).isAboveHorizon();
    }
}
