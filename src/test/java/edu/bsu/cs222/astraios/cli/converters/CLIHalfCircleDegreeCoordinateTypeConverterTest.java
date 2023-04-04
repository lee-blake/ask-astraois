package edu.bsu.cs222.astraios.cli.converters;

import edu.bsu.cs222.astraios.model.astronomy.HalfCircleDegreeCoordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.TypeConversionException;

public class CLIHalfCircleDegreeCoordinateTypeConverterTest {

    @Test
    public void testConvertMessyNegativeLessThanOneDegreeAsteriskFormConvertsCorrectly() {
        String messyNegativeLessThanOneDegreeAsteriskForm = "-0*             17'        59.9";
        CLIHalfCircleDegreeCoordinateTypeConverter converter = new CLIHalfCircleDegreeCoordinateTypeConverter();
        HalfCircleDegreeCoordinate actual = converter.convert(messyNegativeLessThanOneDegreeAsteriskForm);
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(0, 17, 59.9).negate();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertThrowsExceptionOnGarbageInput() {
        String garbage = "CRASH IMMEDIATELY";
        CLIHalfCircleDegreeCoordinateTypeConverter converter = new CLIHalfCircleDegreeCoordinateTypeConverter();
        Assertions.assertThrows(
                TypeConversionException.class,
                () -> converter.convert(garbage)
        );
    }

    @Test
    public void testConvertThrowsExceptionOnInvalidInput() {
        String invalid = "+90°00'01\"";
        CLIHalfCircleDegreeCoordinateTypeConverter converter = new CLIHalfCircleDegreeCoordinateTypeConverter();
        Assertions.assertThrows(
                TypeConversionException.class,
                () -> converter.convert(invalid)
        );
    }
}
