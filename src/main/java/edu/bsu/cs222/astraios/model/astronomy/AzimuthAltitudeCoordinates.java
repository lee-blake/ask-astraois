package edu.bsu.cs222.astraios.model.astronomy;

import edu.bsu.cs222.astraios.model.journal.Header;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

public class AzimuthAltitudeCoordinates {

    private final FullCircleDegreeCoordinate azimuth;
    private final HalfCircleDegreeCoordinate altitude;

    public AzimuthAltitudeCoordinates(FullCircleDegreeCoordinate azimuth, HalfCircleDegreeCoordinate altitude) {
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AzimuthAltitudeCoordinates other) {
            return this.altitude.equals(other.altitude)  && this.azimuth.equals(other.azimuth);
        }
        return false;
    }

    public AngularDistance distanceTo(AzimuthAltitudeCoordinates other) {
        double altitude1 = this.altitude.toRadians();
        double altitude2 = other.altitude.toRadians();
        double azimuth1 = this.azimuth.toRadians();
        double azimuth2 = other.azimuth.toRadians();
        double cosOfAngularDistance = sin(altitude1)*sin(altitude2)
                + cos(altitude1)*cos(altitude2)*cos(azimuth1-azimuth2);
        double angularDistanceRadians = acos(cosOfAngularDistance);
        return AngularDistance.fromRadians(angularDistanceRadians);
    }

    public boolean isAboveHorizon() {
        return this.altitude.toRadians() > 0;
    }

    public double getAltitudeInRadians() {
        return this.altitude.toRadians();
    }



    public class AzimuthAltitudeCoordinatesCLIFormatter {

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
