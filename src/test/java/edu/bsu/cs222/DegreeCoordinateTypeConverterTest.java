package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// Suppressing warnings for the Unicode escaping of the degree character in this file because the fix - replacing
// the escape with the literal character - has caused unmappable character errors when running this project
// with default settings. As the inlining of the characters does not impede the functionality, only the readability,
// and as the context indicates it is the degree character, fixing this warning will be delayed until all team members
// can meet and confirm that the change does not cause errors on their respective systems.
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class DegreeCoordinateTypeConverterTest {

    @Test
    public void testConvertHalfCircleStandardFormZero() {
        String zeroStandardForm = "0\u00b000'00\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(zeroStandardForm);
        HalfCircleDegreeCoordinate zero = new HalfCircleDegreeCoordinate(0,0,0);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        boolean result = zero.equals(actual);
        Assertions.assertTrue(result);
    }

    @Test
    public void testConvertHalfCircleStandardFormPositive() {
        String positiveStandardForm = "+72\u00b036'03.2\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(positiveStandardForm);
        HalfCircleDegreeCoordinate positive = new HalfCircleDegreeCoordinate(72,36,3.2);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        boolean result = positive.equals(actual);
        Assertions.assertTrue(result);
    }

    @Test
    public void testConvertHalfCircleStandardFormNegative() {
        String negativeStandardForm = "-85\u00b003'59.9\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(negativeStandardForm);
        HalfCircleDegreeCoordinate negative = new HalfCircleDegreeCoordinate(-85,3,59.9);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        boolean result = negative.equals(actual);
        Assertions.assertTrue(result);
    }

    @Test
    public void testConvertHalfCircleStandardFormArbitrarySpaces() {
        String negativeStandardForm = "-85          \u00b0  03' 59.9\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(negativeStandardForm);
        HalfCircleDegreeCoordinate negative = new HalfCircleDegreeCoordinate(-85,3,59.9);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        boolean result = negative.equals(actual);
        Assertions.assertTrue(result);
    }
}
