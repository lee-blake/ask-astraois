package edu.bsu.cs222.astraios.model.astronomy;

import edu.bsu.cs222.astraios.model.journal.Header;

import java.util.HashMap;
import java.util.Map;


public class AstronomicalObject {

    private String name;
    private final RightAscensionDeclinationCoordinates celestialCoordinates;

    public AstronomicalObject(String name, RightAscensionDeclinationCoordinates celestialCoordinates) {
        this.name = name;
        this.celestialCoordinates = celestialCoordinates;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AstronomicalObject other) {
            return this.name.equals(other.name) && this.celestialCoordinates.equals(other.celestialCoordinates);
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public AzimuthAltitudeCoordinates getAltAzAtObservation(Observation observation) {
        return this.celestialCoordinates.convertToAltAzAtObservation(observation);
    }

    public void editName(String newName) {
        this.name = newName;
    }

    public void editRightAscension(HourCoordinate newRightAscension) {
        this.celestialCoordinates.editRightAscension(newRightAscension);
    }

    public void editDeclination(HalfCircleDegreeCoordinate newDeclination) {
        this.celestialCoordinates.editDeclination(newDeclination);
    }



    public class AstronomicalObjectCSVFormatter {

        public Map<Header, String> getCSVValueMap() {
            AstronomicalObject parent = AstronomicalObject.this;
            Map<Header, String> objectCSVValueMap = new HashMap<>();
            objectCSVValueMap.put(Header.NAME,parent.name);
            RightAscensionDeclinationCoordinates.RightAscensionDeclinationCoordinatesCSVFormatter coordinatesFormatter
                    = parent.celestialCoordinates.new RightAscensionDeclinationCoordinatesCSVFormatter();
            Map<Header, String> coordinatesCSVValueMap = coordinatesFormatter.getCSVValueMap();
            objectCSVValueMap.putAll(coordinatesCSVValueMap);
            return objectCSVValueMap;
        }
    }



    public class AstronomicalObjectCLIFormatter {

        public Map<Header, String> getCLIValueMap() {
            AstronomicalObject parent = AstronomicalObject.this;
            Map<Header, String> objectCLIValueMap = new HashMap<>();
            objectCLIValueMap.put(Header.NAME,parent.name);
            RightAscensionDeclinationCoordinates.RightAscensionDeclinationCoordinatesCLIFormatter coordinatesFormatter
                    = parent.celestialCoordinates.new RightAscensionDeclinationCoordinatesCLIFormatter();
            Map<Header, String> coordinatesCLIValueMap = coordinatesFormatter.getCLIValueMap();
            objectCLIValueMap.putAll(coordinatesCLIValueMap);
            return objectCLIValueMap;
        }
    }
}
