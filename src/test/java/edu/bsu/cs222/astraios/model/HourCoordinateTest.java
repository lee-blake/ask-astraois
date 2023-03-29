package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HourCoordinateTest {

    @Test
    public void testConstructorNegativeMinutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HourCoordinate(12, -30, 30.0)
        );
    }

    @Test
    public void testConstructorLargeMinutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HourCoordinate(12, 90, 30.0)
        );
    }

    @Test
    public void testConstructorNegativeSecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HourCoordinate(12, 30, -30.0)
        );
    }

    @Test
    public void testConstructorLargeSecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HourCoordinate(12, 30, 60.0)
        );
    }

    @Test
    public void testConstructorBarelyValidSecondsNoException() {
        new HalfCircleDegreeCoordinate(12, 30, 59.9999999999);
    }



    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonHourCoordinateNotEqual() {
        HourCoordinate coordinate = new HourCoordinate(4, 30, 30.0);
        String otherTypeObject = "";
        boolean result = coordinate.equals(otherTypeObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameUnitValueIsEqual() {
        HourCoordinate coordinate0 = new HourCoordinate(4, 30, 30.0);
        HourCoordinate coordinate1 = new HourCoordinate(4, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentHoursNotEqual() {
        HourCoordinate coordinate0 = new HourCoordinate(4, 30, 30.0);
        HourCoordinate coordinate1 = new HourCoordinate(3, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentMinutesNotEqual() {
        HourCoordinate coordinate0 = new HourCoordinate(4, 30, 30.0);
        HourCoordinate coordinate1 = new HourCoordinate(4, 29, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentSecondsNotEqual() {
        HourCoordinate coordinate0 = new HourCoordinate(4, 30, 30.0);
        HourCoordinate coordinate1 = new HourCoordinate(4, 30, 29.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsNegationNotEqual() {
        HourCoordinate coordinate0 = new HourCoordinate(4, 30, 30.0);
        HourCoordinate coordinate1 = new HourCoordinate(-4, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferenceBelowResolutionEqual() {
        HourCoordinate coordinate0 = new HourCoordinate(4, 30, 30.0);
        HourCoordinate coordinate1 = new HourCoordinate(4, 30, 30.000006);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsNegationAndComplementAreEqual() {
        HourCoordinate negation = new HourCoordinate(-4, 30, 30.0);
        HourCoordinate complement = new HourCoordinate(19, 29, 30.0);
        boolean result = negation.equals(complement);
        Assertions.assertTrue(result);
    }



    @Test
    public void testToRadiansMidnightConvertsCorrectly() {
        HourCoordinate midnight = new HourCoordinate(0,0,0);
        double expected = 0;
        double actual = midnight.toRadians();
        Assertions.assertEquals(expected,actual,0.000000001);
    }

    @Test
    public void testToRadiansNoonConvertsCorrectly() {
        HourCoordinate noon = new HourCoordinate(12,0,0);
        double expected = Math.PI;
        double actual = noon.toRadians();
        Assertions.assertEquals(expected,actual,0.000000001);
    }



    @Test
    public void testFromRadiansMidnightConstructsCorrectly() {
        HourCoordinate expected = new HourCoordinate(0,0,0);
        HourCoordinate actual = HourCoordinate.fromRadians(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFromRadiansNoonConstructsCorrectly() {
        HourCoordinate expected = new HourCoordinate(12,0,0);
        HourCoordinate actual = HourCoordinate.fromRadians(Math.PI);
        Assertions.assertEquals(expected,actual);
    }



    @Test
    public void testNegateZeroToZero() {
        HourCoordinate expected = new HourCoordinate(0,0,0);
        HourCoordinate actual = new HourCoordinate(0,0,0).negate();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testNegate6amTo6pm() {
        HourCoordinate expected = new HourCoordinate(-6,0,0);
        HourCoordinate actual = new HourCoordinate(6,0,0).negate();
        Assertions.assertEquals(expected,actual);
    }
}
