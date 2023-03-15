package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DegreeCoordinateTypeConverterTest {

    @Test
    public void testConvertHalfCircleStandardFormZero() {
        String zeroStandardForm = "0°00'00\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(zeroStandardForm);
        HalfCircleDegreeCoordinate zero = new HalfCircleDegreeCoordinate(0,0,0);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        Assertions.assertEquals(zero,actual);
    }

    @Test
    public void testConvertHalfCircleStandardFormPositive() {
        String positiveStandardForm = "+72°36'03.2\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(positiveStandardForm);
        HalfCircleDegreeCoordinate positive = new HalfCircleDegreeCoordinate(72,36,3.2);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        Assertions.assertEquals(positive,actual);
    }

    @Test
    public void testConvertHalfCircleStandardFormNegative() {
        String negativeStandardForm = "-85°03'59.9\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(negativeStandardForm);
        HalfCircleDegreeCoordinate negative = new HalfCircleDegreeCoordinate(-85,3,59.9);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        Assertions.assertEquals(negative,actual);
    }

    @Test
    public void testConvertHalfCircleStandardFormArbitrarySpaces() {
        String negativeStandardForm = "-85          °  03' 59.9\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(negativeStandardForm);
        HalfCircleDegreeCoordinate negative = new HalfCircleDegreeCoordinate(-85,3,59.9);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        Assertions.assertEquals(negative,actual);
    }
}
