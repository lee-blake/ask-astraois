package edu.bsu.cs222.astraios.model.converters;

import edu.bsu.cs222.astraios.model.astronomy.HourCoordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HourCoordinateTypeConverterTest {

    @Test
    public void testConvertStandardFormZero() {
        String zeroStandardForm = "00h00m00s";
        HourCoordinateTypeConverter converter = new HourCoordinateTypeConverter(zeroStandardForm);
        HourCoordinate expected = new HourCoordinate(0, 0, 0);
        HourCoordinate actual = converter.convert();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertStandardFormLastMilli() {
        String lastMilliStandardForm = "23h59m59.999s";
        HourCoordinateTypeConverter converter = new HourCoordinateTypeConverter(lastMilliStandardForm);
        HourCoordinate expected = new HourCoordinate(23, 59, 59.999);
        HourCoordinate actual = converter.convert();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertStandardFormArbitrarySpaces() {
        String arbitrarySpacesInStandardForm = "23h           59m  59.999s   ";
        HourCoordinateTypeConverter converter = new HourCoordinateTypeConverter(arbitrarySpacesInStandardForm);
        HourCoordinate expected = new HourCoordinate(23, 59, 59.999);
        HourCoordinate actual = converter.convert();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertStandardFormNegativeLessThanOneHour() {
        String negativeLessThanOneHour = "-0h 2m 7s";
        HourCoordinateTypeConverter converter = new HourCoordinateTypeConverter(negativeLessThanOneHour);
        HourCoordinate actual = converter.convert();
        HourCoordinate expected = new HourCoordinate(0, 2, 7).negate();
        Assertions.assertEquals(expected, actual);
    }
}
