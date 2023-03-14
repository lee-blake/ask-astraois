package edu.bsu.cs222.astraios.model;

public class HourCoordinateTypeConverter {

    private int hours;
    private int minutes;
    private double seconds;

    public HourCoordinateTypeConverter(String stringToConvert) {
        String compactString = removeWhitespace(stringToConvert);
        this.parseStandardFormat(compactString);
    }

    private String removeWhitespace(String stringToStrip) {
        return stringToStrip.replace(" ","");
    }

    public HourCoordinate convert() {
        return new HourCoordinate(
                this.hours,
                this.minutes,
                this.seconds
        );
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
}
