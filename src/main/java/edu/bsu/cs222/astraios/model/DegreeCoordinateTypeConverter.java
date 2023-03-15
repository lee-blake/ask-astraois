package edu.bsu.cs222.astraios.model;

public class DegreeCoordinateTypeConverter {

    private int degrees;
    private int arcminutes;
    private double arcseconds;

    public DegreeCoordinateTypeConverter(String stringToConvert) {
        String compactString = removeWhitespace(stringToConvert);
        // Standard format: 41\00b0 54' 43"
        this.parseStandardFormat(compactString);
    }

    private String removeWhitespace(String stringToStrip) {
        return stringToStrip.replace(" ","");
    }

    private void parseStandardFormat(String stringToConvert) {
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
        // This may seem dangerous since the values are not initialized, but any non-initialization results
        // in a throw in the constructor, so we only get this far if everything is OK in terms of parsing.
        // The values may not be valid, however.
        return new HalfCircleDegreeCoordinate(
                this.degrees,
                this.arcminutes,
                this.arcseconds
        );
    }
}
