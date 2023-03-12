package edu.bsu.cs222;

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

    // Suppressing warnings for the Unicode escaping of the degree character in this file because the fix -
    // replacing the escape with the literal character - has caused unmappable character errors when running this
    // project with default settings. As the inlining of the characters does not impede the functionality, only the
    // readability, and as the context indicates it is the degree character, fixing this warning will be delayed
    // until all team members can meet and confirm that the change does not cause errors on their
    // respective systems.
    @SuppressWarnings("UnnecessaryUnicodeEscape")
    private void parseStandardFormat(String stringToConvert) {
        String degreesToken = stringToConvert.split("\u00b0")[0];
        String arcminutesToken = stringToConvert.split("\u00b0")[1]
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
