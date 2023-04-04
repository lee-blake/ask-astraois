package edu.bsu.cs222.astraios.model.converters;

import edu.bsu.cs222.astraios.model.astronomy.FullCircleDegreeCoordinate;
import edu.bsu.cs222.astraios.model.astronomy.HalfCircleDegreeCoordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DegreeCoordinateTypeConverterTest {

    @Test
    public void testConvertHalfCircleStandardFormZero() {
        String zeroStandardForm = "0°00'00\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(zeroStandardForm);
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(0, 0, 0);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertHalfCircleStandardFormPositive() {
        String positiveStandardForm = "+72°36'03.2\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(positiveStandardForm);
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(72, 36, 3.2);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertHalfCircleStandardFormNegative() {
        String negativeStandardForm = "-85°03'59.9\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(negativeStandardForm);
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(-85, 3, 59.9);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertHalfCircleStandardFormArbitrarySpaces() {
        String arbitrarySpacesInStandardForm = "-85          °  03' 59.9\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(arbitrarySpacesInStandardForm);
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(-85, 3, 59.9);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertHalfCircleStandardFormNegativeLessThanOneDegree() {
        String negativeLessThanOneDegree = "-0° 1' 3\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(negativeLessThanOneDegree);
        HalfCircleDegreeCoordinate actual = converter.convertHalfCircle();
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(0, 1, 3).negate();
        Assertions.assertEquals(expected, actual);
    }



    @Test
    public void testConvertFullCircleStandardFormNegativeLessThanOneDegree() {
        String negativeLessThanOneDegree = "-0° 1' 3\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(negativeLessThanOneDegree);
        FullCircleDegreeCoordinate actual = converter.convertFullCircle();
        FullCircleDegreeCoordinate expected = new FullCircleDegreeCoordinate(0 ,1 ,3).negate();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertFullCircleStandardFormHighModulus() {
        String highModulus = "362° 17' 59.9\"";
        DegreeCoordinateTypeConverter converter = new DegreeCoordinateTypeConverter(highModulus);
        FullCircleDegreeCoordinate actual = converter.convertFullCircle();
        FullCircleDegreeCoordinate expected = new FullCircleDegreeCoordinate(2, 17, 59.9);
        Assertions.assertEquals(expected, actual);
    }
}
