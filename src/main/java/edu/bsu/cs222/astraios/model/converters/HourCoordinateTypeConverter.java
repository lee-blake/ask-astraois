package edu.bsu.cs222.astraios.model.converters;

import edu.bsu.cs222.astraios.model.astronomy.HourCoordinate;

public class HourCoordinateTypeConverter {

    private boolean negative;
    private int hours;
    private int minutes;
    private double seconds;

    public HourCoordinateTypeConverter(String stringToConvert) {
        String compactString = removeWhitespace(stringToConvert);
        String noLongerNegativeString = extractNegativeSignForConversion(compactString);
        this.parseStandardFormat(noLongerNegativeString);
    }

    private String removeWhitespace(String stringToStrip) {
        return stringToStrip.replace(" ","");
    }

    private String extractNegativeSignForConversion(String potentiallyNegativeString) {
        if(potentiallyNegativeString.startsWith("-")) {
            this.negative = true;
            return potentiallyNegativeString.substring(1);
        }
        else {
            this.negative = false;
            return potentiallyNegativeString;
        }
    }

    private void parseStandardFormat(String stringToConvert) {
        String hoursToken = stringToConvert.split("h")[0];
        String minutesToken = stringToConvert.split("h")[1]
                .split("m")[0];
        String secondsToken = stringToConvert.split("m")[1]
                .replace("s","");
        this.hours = Integer.parseInt(hoursToken);
        this.minutes = Integer.parseInt(minutesToken);
        this.seconds = Double.parseDouble(secondsToken);
    }

    public HourCoordinate convert() {
        HourCoordinate coordinate = new HourCoordinate(
                this.hours,
                this.minutes,
                this.seconds
        );
        if(this.negative) {
            coordinate = coordinate.negate();
        }
        return coordinate;
    }

}
