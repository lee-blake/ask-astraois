package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// Suppressing warnings for the Unicode escaping of the degree character in this file because the fix - replacing
// the escape with the literal character - has caused unmappable character errors when running this project
// with default settings. As the inlining of the characters does not impede the functionality, only the readability,
// and as the context indicates it is the degree character, fixing this warning will be delayed until all team members
// can meet and confirm that the change does not cause errors on their respective systems.
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class RightAscensionDeclinationTypeConverterTest {

    @Test
    public void testConvertStandardFormatZero() {
        String rightAscensionOfZeroStandardForm = "00h00m00s";
        String declinationOfZeroStandardForm = "0\u00b000'00\"";
        RightAscensionDeclinationTypeConverter converter = new RightAscensionDeclinationTypeConverter(
                rightAscensionOfZeroStandardForm,
                declinationOfZeroStandardForm
        );
        RightAscensionDeclinationCoordinates zero = new RightAscensionDeclinationCoordinates(
            new HourCoordinate(0,0,0),
            new HalfCircleDegreeCoordinate(0,0,0)
        );
        RightAscensionDeclinationCoordinates actual = converter.convert();
        Assertions.assertEquals(zero,actual);
    }

    @Test
    public void testConvertStandardFormatM13Coords() {
        String rightAscensionOfM13StandardForm = "16h41m41.24s";
        String declinationOfM13StandardForm = "+36\u00b027'35.5\"";
        RightAscensionDeclinationTypeConverter converter = new RightAscensionDeclinationTypeConverter(
                rightAscensionOfM13StandardForm,
                declinationOfM13StandardForm
        );
        RightAscensionDeclinationCoordinates m13Coords = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(16,41,41.24),
                new HalfCircleDegreeCoordinate(36,27,35.5)
        );
        RightAscensionDeclinationCoordinates actual = converter.convert();
        Assertions.assertEquals(m13Coords,actual);
    }
}
