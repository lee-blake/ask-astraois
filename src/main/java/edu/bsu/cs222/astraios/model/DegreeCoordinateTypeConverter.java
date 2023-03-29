package edu.bsu.cs222.astraios.model;

public class DegreeCoordinateTypeConverter {

    private boolean negative;
    private int degrees;
    private int arcminutes;
    private double arcseconds;

    public DegreeCoordinateTypeConverter(String stringToConvert) {
        String compactString = removeWhitespace(stringToConvert);
        String noLongerNegativeString = extractNegative(compactString);
        this.parseStandardFormat(noLongerNegativeString);
    }

    private String removeWhitespace(String stringToStrip) {
        return stringToStrip.replace(" ","");
    }

    private String extractNegative(String potentiallyNegativeString) {
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
        // Standard format: 41° 54' 43"
        String degreesToken = stringToConvert.split("°")[0];
        String arcminutesToken = stringToConvert.split("°")[1]
                .split("'")[0];
        String arcsecondsToken = stringToConvert.split("'")[1]
                .replace("\"","");
        this.degrees = Integer.parseInt(degreesToken);
        this.arcminutes = Integer.parseInt(arcminutesToken);
        this.arcseconds = Double.parseDouble(arcsecondsToken);
    }

    public HalfCircleDegreeCoordinate convertHalfCircle() {
        HalfCircleDegreeCoordinate coordinate = new HalfCircleDegreeCoordinate(
                this.degrees,
                this.arcminutes,
                this.arcseconds
        );
        if(this.negative) {
            coordinate = coordinate.negate();
        }
        return coordinate;
    }
}
