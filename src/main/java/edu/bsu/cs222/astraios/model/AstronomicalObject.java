package edu.bsu.cs222.astraios.model;

import java.util.HashMap;
import java.util.Map;


public class AstronomicalObject {

    private final String name;
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


    public AltitudeAzimuthCoordinates getAltAzAtObservation(Observation observation) {
        return this.celestialCoordinates.convertToAltAzAtObservation(observation);
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
            Map<Header, String> objectCLIViewValueMap = new HashMap<>();
            objectCLIViewValueMap.put(Header.NAME,parent.name);
            RightAscensionDeclinationCoordinates
                    .RightAscensionDeclinationCoordinatesCLIViewFormatter coordinatesFormatter
                    = parent.celestialCoordinates.new RightAscensionDeclinationCoordinatesCLIViewFormatter();
            Map<Header, String> coordinatesCLIViewValueMap = coordinatesFormatter.getCLIViewValueMap();
            objectCLIViewValueMap.putAll(coordinatesCLIViewValueMap);
            return objectCLIViewValueMap;
        }
    }
}
