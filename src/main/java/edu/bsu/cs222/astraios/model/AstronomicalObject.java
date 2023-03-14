package edu.bsu.cs222.astraios.model;

import java.util.HashMap;
import java.util.Map;

public class AstronomicalObject {

    private final String name;
    private final RightAscensionDeclinationCoordinates raDecCoords;

    public AstronomicalObject(String name, RightAscensionDeclinationCoordinates raDecCoords) {
        this.name = name;
        this.raDecCoords = raDecCoords;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AstronomicalObject other) {
            return this.name.equals(other.name) && this.raDecCoords.equals(other.raDecCoords);
        }
        return false;
    }

    public String getName() {
        return this.name;
    }



    public class AstronomicalObjectCSVFormatter {

        public Map<Header, String> getCSVValueMap() {
            AstronomicalObject parent = AstronomicalObject.this;
            Map<Header, String> objectCSVValueMap = new HashMap<>();
            objectCSVValueMap.put(Header.NAME,parent.name);
            RightAscensionDeclinationCoordinates.RightAscensionDeclinationCoordinatesCSVFormatter raDecFormatter
                    = parent.raDecCoords.new RightAscensionDeclinationCoordinatesCSVFormatter();
            Map<Header, String> raDecCoordsCSVValueMap = raDecFormatter.getCSVValueMap();
            objectCSVValueMap.putAll(raDecCoordsCSVValueMap);
            return objectCSVValueMap;
        }
    }

    public class AstronomicalObjectCLIViewFormatter {

        public Map<Header, String> getCLIViewValueMap() {
            AstronomicalObject parent = AstronomicalObject.this;
            Map<Header, String> objectCLIViewValueMap = new HashMap<>();
            objectCLIViewValueMap.put(Header.NAME,parent.name);
            RightAscensionDeclinationCoordinates.RightAscensionDeclinationCoordinatesCLIViewFormatter raDecFormatter
                    = parent.raDecCoords.new RightAscensionDeclinationCoordinatesCLIViewFormatter();
            Map<Header, String> raDecCoordsCSVValueMap = raDecFormatter.getCLIViewValueMap();
            objectCLIViewValueMap.putAll(raDecCoordsCSVValueMap);
            return objectCLIViewValueMap;
        }
    }
}
