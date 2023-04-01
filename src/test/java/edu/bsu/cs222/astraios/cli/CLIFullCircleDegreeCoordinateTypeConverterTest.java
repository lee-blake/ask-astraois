package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.FullCircleDegreeCoordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.TypeConversionException;

public class CLIFullCircleDegreeCoordinateTypeConverterTest {

    @Test
    public void testConvertMessyNegativeLessThanOneDegreeAsteriskFormConvertsCorrectly() {
        String messyNegativeLessThanOneDegreeAsteriskForm = "-0*             17'        59.9";
        CLIFullCircleDegreeCoordinateTypeConverter converter = new CLIFullCircleDegreeCoordinateTypeConverter();
        FullCircleDegreeCoordinate actual = converter.convert(messyNegativeLessThanOneDegreeAsteriskForm);
        FullCircleDegreeCoordinate expected = new FullCircleDegreeCoordinate(0,17,59.9).negate();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testConvertThrowsExceptionOnGarbageInput() {
        String garbage = "CRASH IMMEDIATELY";
        CLIFullCircleDegreeCoordinateTypeConverter converter = new CLIFullCircleDegreeCoordinateTypeConverter();
        Assertions.assertThrows(
                TypeConversionException.class,
                () -> converter.convert(garbage)
        );
    }
}
