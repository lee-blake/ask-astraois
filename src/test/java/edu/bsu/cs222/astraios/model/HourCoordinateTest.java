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
        String randomObject = "";
        boolean result = coordinate.equals(randomObject);
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
}