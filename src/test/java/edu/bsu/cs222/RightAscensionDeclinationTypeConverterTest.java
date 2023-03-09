package edu.bsu.cs222;

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
        String raZero = "00h00m00s";
        String decZero = "0\u00b000'00\"";
        RightAscensionDeclinationTypeConverter converter = new RightAscensionDeclinationTypeConverter(
                raZero,
                decZero
        );
        RightAscensionDeclinationCoordinates zero = new RightAscensionDeclinationCoordinates(
            new HourCoordinate(0,0,0),
            new HalfCircleDegreeCoordinate(0,0,0)
        );
        RightAscensionDeclinationCoordinates actual = converter.convert();
        boolean result = zero.equals(actual);
        Assertions.assertTrue(result);
    }

    @Test
    public void testConvertStandardFormatM13Coords() {
        String raM13 = "16h41m41.24s";
        String decM13 = "+36\u00b027'35.5\"";
        RightAscensionDeclinationTypeConverter converter = new RightAscensionDeclinationTypeConverter(
                raM13,
                decM13
        );
        RightAscensionDeclinationCoordinates m13Coords = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(16,41,41.24),
                new HalfCircleDegreeCoordinate(36,27,35.5)
        );
        RightAscensionDeclinationCoordinates actual = converter.convert();
        boolean result = m13Coords.equals(actual);
        Assertions.assertTrue(result);
    }
}
