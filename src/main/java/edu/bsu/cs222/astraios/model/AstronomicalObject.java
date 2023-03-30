package edu.bsu.cs222.astraios.model;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

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
        double ha = observation.getLocalSiderealTime().toRadians()
                - this.celestialCoordinates.getRA().toRadians();
        double dec = this.celestialCoordinates.getDec().toRadians();
        double lat = observation.getLatitudeAsRadians();
        double sinOfAltitude = sin(dec)*sin(lat) + cos(dec)*cos(lat)*cos(ha);

        double alt = Math.asin(sinOfAltitude);
        double cosOfAzimuth = (sin(dec) - sin(alt)*sin(lat))/(cos(alt)*cos(lat));
        double azimuthAsRadians = Math.acos(cosOfAzimuth);

        if(sin(ha) > 0) {
            azimuthAsRadians *= -1;
        }
        return new AltitudeAzimuthCoordinates(
                FullCircleDegreeCoordinate.fromRadians(azimuthAsRadians),
                HalfCircleDegreeCoordinate.fromRadians(alt)
        );
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

    public class AstronomicalObjectCLIViewFormatter {

        public Map<Header, String> getCLIViewValueMap() {
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
