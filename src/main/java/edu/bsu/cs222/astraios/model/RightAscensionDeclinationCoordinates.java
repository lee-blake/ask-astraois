package edu.bsu.cs222.astraios.model;

import java.util.HashMap;
import java.util.Map;

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

    public class RightAscensionDeclinationCoordinatesCSVFormatter {

        public Map<Header, String> getCSVValueMap() {
            RightAscensionDeclinationCoordinates parent = RightAscensionDeclinationCoordinates.this;
            Map<Header,String> csvValueMap = new HashMap<>();
            HourCoordinate.HourCoordinateFormatter raFormatter = parent.rightAscension.new HourCoordinateFormatter();
            csvValueMap.put(Header.RIGHT_ASCENSION,raFormatter.standardHourFormatSpaced());
            HalfCircleDegreeCoordinate.HalfCircleDegreeCoordinateFormatter decFormatter
                    = parent.declination.new HalfCircleDegreeCoordinateFormatter();
            String declinationString = decFormatter.standardDegreeFormatSpaced();
            if(!declinationString.contains("-")) {
                declinationString = "+" + declinationString;
            }
            csvValueMap.put(Header.DECLINATION,declinationString);
            return csvValueMap;
        }
    }

    public class RightAscensionDeclinationCoordinatesCLIViewFormatter {

        public Map<Header, String> getCLIViewValueMap() {
            RightAscensionDeclinationCoordinates parent = RightAscensionDeclinationCoordinates.this;
            Map<Header,String> cliViewValueMap = new HashMap<>();
            HourCoordinate.HourCoordinateFormatter raFormatter = parent.rightAscension.new HourCoordinateFormatter();
            cliViewValueMap.put(Header.RIGHT_ASCENSION,raFormatter.standardHourFormatNoSpacesOneDecimalPlace());
            HalfCircleDegreeCoordinate.HalfCircleDegreeCoordinateFormatter decFormatter
                    = parent.declination.new HalfCircleDegreeCoordinateFormatter();
            String declinationString = decFormatter.standardDegreeFormatNoSpacesOneDecimalPlace();
            if(!declinationString.contains("-")) {
                declinationString = "+" + declinationString;
            }
            cliViewValueMap.put(Header.DECLINATION,declinationString);
            return cliViewValueMap;
        }
    }
}
