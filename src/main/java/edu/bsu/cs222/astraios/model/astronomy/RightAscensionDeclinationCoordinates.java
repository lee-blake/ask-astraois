package edu.bsu.cs222.astraios.model.astronomy;

import edu.bsu.cs222.astraios.model.journal.Header;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class RightAscensionDeclinationCoordinates {

    private HourCoordinate rightAscension;
    private HalfCircleDegreeCoordinate declination;

    public RightAscensionDeclinationCoordinates(HourCoordinate ra, HalfCircleDegreeCoordinate dec) {
        this.rightAscension = ra;
        this.declination = dec;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof RightAscensionDeclinationCoordinates other) {
            return this.rightAscension.equals(other.rightAscension) && this.declination.equals(other.declination);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[RA " + this.rightAscension + " DEC " + this.declination + "]";
    }

    public AzimuthAltitudeCoordinates convertToAltAzAtObservation(Observation observation) {
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
        return new AzimuthAltitudeCoordinates(
                FullCircleDegreeCoordinate.fromRadians(azimuthAsRadians),
                HalfCircleDegreeCoordinate.fromRadians(altitude)
        );
    }

    public void editRightAscension(HourCoordinate newRightAscension) { this.rightAscension = newRightAscension;}

    public void editDeclination(HalfCircleDegreeCoordinate newDeclination) { this.declination = newDeclination;}



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
