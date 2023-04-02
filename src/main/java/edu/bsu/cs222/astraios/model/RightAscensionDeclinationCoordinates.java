package edu.bsu.cs222.astraios.model;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class RightAscensionDeclinationCoordinates {

    private final HourCoordinate rightAscension;
    private final HalfCircleDegreeCoordinate declination;

    public RightAscensionDeclinationCoordinates(HourCoordinate ra, HalfCircleDegreeCoordinate dec) {
        this.rightAscension = ra;
        this.declination = dec;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof RightAscensionDeclinationCoordinates other) {
            return this.rightAscension.equals(other.rightAscension) && this.declination.equals(other.declination);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[RA " + this.rightAscension + " DEC " + this.declination + "]";
    }

    public AltitudeAzimuthCoordinates convertToAltAzAtObservation(Observation observation) {
        double hourAngle = observation.getLocalSiderealTime().toRadians()
                - this.rightAscension.toRadians();
        double declination = this.declination.toRadians();
        double latitude = observation.getLatitudeAsRadians();
        double sinOfAltitude = sin(declination)*sin(latitude)
                + cos(declination)*cos(latitude)*cos(hourAngle);
        double altitude = Math.asin(sinOfAltitude);
        double cosOfAzimuth = (sin(declination) - sin(altitude)*sin(latitude))
                / (cos(altitude)*cos(latitude));
        double azimuthAsRadians = Math.acos(cosOfAzimuth);
        if(sin(hourAngle) > 0) {
            azimuthAsRadians *= -1;
        }
        return new AltitudeAzimuthCoordinates(
                FullCircleDegreeCoordinate.fromRadians(azimuthAsRadians),
                HalfCircleDegreeCoordinate.fromRadians(altitude)
        );
    }


    public class RightAscensionDeclinationCoordinatesCSVFormatter {

        public Map<Header, String> getCSVValueMap() {
            RightAscensionDeclinationCoordinates parent = RightAscensionDeclinationCoordinates.this;
            Map<Header,String> csvValueMap = new HashMap<>();
            HourCoordinate.HourCoordinateFormatter raFormatter = parent.rightAscension.new HourCoordinateFormatter();
            csvValueMap.put(Header.RIGHT_ASCENSION,raFormatter.standardHourFormatSpaced());
            HalfCircleDegreeCoordinate.HalfCircleDegreeCoordinateFormatter decFormatter
                    = parent.declination.new HalfCircleDegreeCoordinateFormatter();
            String declinationString = decFormatter.standardDegreeFormatSpaced();
            csvValueMap.put(Header.DECLINATION,declinationString);
            return csvValueMap;
        }
    }



    public class RightAscensionDeclinationCoordinatesCLIFormatter {

        public Map<Header, String> getCLIValueMap() {
            RightAscensionDeclinationCoordinates parent = RightAscensionDeclinationCoordinates.this;
            Map<Header,String> cliValueMap = new HashMap<>();
            HourCoordinate.HourCoordinateFormatter raFormatter = parent.rightAscension.new HourCoordinateFormatter();
            cliValueMap.put(Header.RIGHT_ASCENSION,raFormatter.standardHourFormatNoSpacesOneDecimalPlace());
            HalfCircleDegreeCoordinate.HalfCircleDegreeCoordinateFormatter decFormatter
                    = parent.declination.new HalfCircleDegreeCoordinateFormatter();
            String declinationString = decFormatter.standardDegreeFormatNoSpacesOneDecimalPlace();
            cliValueMap.put(Header.DECLINATION,declinationString);
            return cliValueMap;
        }
    }
}
