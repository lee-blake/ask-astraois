package edu.bsu.cs222.astraios.cli.converters;

import edu.bsu.cs222.astraios.model.astronomy.HourCoordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.TypeConversionException;

public class CLIHourCoordinateTypeConverterTest {

    @Test
    public void testConvertMessyNegativeLessThanOneHourConvertsCorrectly() {
        String messyNegativeLessThanOneDegreeAsteriskForm = "-0h             17m        59.9s";
        CLIHourCoordinateTypeConverter converter = new CLIHourCoordinateTypeConverter();
        HourCoordinate actual = converter.convert(messyNegativeLessThanOneDegreeAsteriskForm);
        HourCoordinate expected = new HourCoordinate(0, 17, 59.9).negate();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertThrowsExceptionOnGarbageInput() {
        String garbageInput = "CRASH IMMEDIATELY";
        CLIHourCoordinateTypeConverter converter = new CLIHourCoordinateTypeConverter();
        Assertions.assertThrows(
                TypeConversionException.class,
                () -> converter.convert(garbageInput)
        );
    }
}
