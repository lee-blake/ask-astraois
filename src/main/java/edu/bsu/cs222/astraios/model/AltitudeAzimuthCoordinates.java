package edu.bsu.cs222.astraios.model;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

public class AltitudeAzimuthCoordinates {

    private final FullCircleDegreeCoordinate azimuth;
    private final HalfCircleDegreeCoordinate altitude;

    public AltitudeAzimuthCoordinates(FullCircleDegreeCoordinate azimuth, HalfCircleDegreeCoordinate altitude) {
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AltitudeAzimuthCoordinates other) {
            return this.altitude.equals(other.altitude)  && this.azimuth.equals(other.azimuth);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ALT " + this.altitude + " AZ " + this.azimuth + "]";
    }

    public AngularDistance distanceTo(AltitudeAzimuthCoordinates other) {
        double altitude1 = this.altitude.toRadians();
        double altitude2 = other.altitude.toRadians();
        double azimuth1 = this.azimuth.toRadians();
        double azimuth2 = other.azimuth.toRadians();
        double angularDistanceRadians = acos(
                sin(altitude1)*sin(altitude2)
                        + cos(altitude1)*cos(altitude2)*cos(azimuth1-azimuth2)
        );
        return AngularDistance.fromRadians(angularDistanceRadians);
    }

    public boolean isAboveHorizon() {
        return this.altitude.toRadians() > 0;
    }

    public double getAltitudeInRadians() {
        return this.altitude.toRadians();
    }



    public class AltitudeAzimuthCoordinatesCLIFormatter {

        public Map<Header, String> getCLIValueMap() {
            Map<Header,String> cliValueMap = new HashMap<>();
            FullCircleDegreeCoordinate.FullCircleDegreeCoordinateFormatter azFormatter =
                    azimuth.new FullCircleDegreeCoordinateFormatter();
            String azimuthString = azFormatter.standardDegreeFormatNoSpacesOneDecimalPlace();
            cliValueMap.put(Header.AZIMUTH,azimuthString);
            HalfCircleDegreeCoordinate.HalfCircleDegreeCoordinateFormatter altFormatter
                    = altitude.new HalfCircleDegreeCoordinateFormatter();
            String altitudeString = altFormatter.standardDegreeFormatNoSpacesOneDecimalPlace();
            cliValueMap.put(Header.ALTITUDE,altitudeString);
            return cliValueMap;
        }
    }
}
