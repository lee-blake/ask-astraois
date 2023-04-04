package edu.bsu.cs222.astraios.model.converters;

import edu.bsu.cs222.astraios.model.astronomy.HalfCircleDegreeCoordinate;
import edu.bsu.cs222.astraios.model.astronomy.HourCoordinate;
import edu.bsu.cs222.astraios.model.astronomy.RightAscensionDeclinationCoordinates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RightAscensionDeclinationTypeConverterTest {

    @Test
    public void testConvertStandardFormatZero() {
        String rightAscensionOfZeroStandardForm = "00h00m00s";
        String declinationOfZeroStandardForm = "0°00'00\"";
        RightAscensionDeclinationTypeConverter converter = new RightAscensionDeclinationTypeConverter(
                rightAscensionOfZeroStandardForm,
                declinationOfZeroStandardForm
        );
        RightAscensionDeclinationCoordinates zero = new RightAscensionDeclinationCoordinates(
            new HourCoordinate(0, 0, 0),
            new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        RightAscensionDeclinationCoordinates actual = converter.convert();
        Assertions.assertEquals(zero, actual);
    }

    @Test
    public void testConvertStandardFormatM13Coords() {
        String rightAscensionOfM13StandardForm = "16h41m41.24s";
        String declinationOfM13StandardForm = "+36°27'35.5\"";
        RightAscensionDeclinationTypeConverter converter = new RightAscensionDeclinationTypeConverter(
                rightAscensionOfM13StandardForm,
                declinationOfM13StandardForm
        );
        RightAscensionDeclinationCoordinates m13Coords = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(16, 41, 41.24),
                new HalfCircleDegreeCoordinate(36, 27, 35.5)
        );
        RightAscensionDeclinationCoordinates actual = converter.convert();
        Assertions.assertEquals(m13Coords, actual);
    }
}
