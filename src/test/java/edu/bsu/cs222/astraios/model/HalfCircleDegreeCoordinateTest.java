package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HalfCircleDegreeCoordinateTest {

    @Test
    public void testConstructorNegativeArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(45, -30, 30.0)
        );
    }

    @Test
    public void testConstructorLargeArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(45, 300, 30.0)
        );
    }

    @Test
    public void testConstructorNegativeArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(45, 30, -30.0)
        );
    }

    @Test
    public void testConstructorLargeArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(45, 30, 60.0)
        );
    }

    @Test
    public void testConstructorBarelyValidArcsecondsNoException() {
        new HalfCircleDegreeCoordinate(45, 30, 59.9999999999);
    }

    @Test
    public void testConstructorLargeNegativeDegreesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(-96, 30, 60.0)
        );
    }

    @Test
    public void testConstructorLargePositiveDegreesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(96, 30, 60.0)
        );
    }

    @Test
    public void testConstructorNorthPoleArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(90, 1, 0)
        );
    }

    @Test
    public void testConstructorNorthPoleArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(90, 0, 1)
        );
    }

    @Test
    public void testConstructorSouthPoleArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(-90, 1, 0)
        );
    }

    @Test
    public void testConstructorSouthPoleArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new HalfCircleDegreeCoordinate(-90, 0, 1)
        );
    }



    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonHalfCircleDegreeCoordinateNotEqual() {
        HalfCircleDegreeCoordinate coordinate = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        String otherTypeObject = "";
        boolean result = coordinate.equals(otherTypeObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameUnitValueIsEqual() {
        HalfCircleDegreeCoordinate coordinate0 = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        HalfCircleDegreeCoordinate coordinate1 = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentDegreeNotEqual() {
        HalfCircleDegreeCoordinate coordinate0 = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        HalfCircleDegreeCoordinate coordinate1 = new HalfCircleDegreeCoordinate(44, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentArcMinuteNotEqual() {
        HalfCircleDegreeCoordinate coordinate0 = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        HalfCircleDegreeCoordinate coordinate1 = new HalfCircleDegreeCoordinate(45, 29, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentArcSecondNotEqual() {
        HalfCircleDegreeCoordinate coordinate0 = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        HalfCircleDegreeCoordinate coordinate1 = new HalfCircleDegreeCoordinate(45, 30, 29.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsNegationNotEqual() {
        HalfCircleDegreeCoordinate coordinate0 = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        HalfCircleDegreeCoordinate coordinate1 = new HalfCircleDegreeCoordinate(-45, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferenceBelowResolutionEqual() {
        HalfCircleDegreeCoordinate coordinate0 = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        HalfCircleDegreeCoordinate coordinate1 = new HalfCircleDegreeCoordinate(45, 30, 30.0001);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsPolesNotEqual() {
        HalfCircleDegreeCoordinate northPole = new HalfCircleDegreeCoordinate(90, 0, 0);
        HalfCircleDegreeCoordinate southPole = new HalfCircleDegreeCoordinate(-90, 0, 0);
        boolean result = northPole.equals(southPole);
        Assertions.assertFalse(result);
    }



    @Test
    public void testToRadiansZeroConvertsCorrectly() {
        HalfCircleDegreeCoordinate zero = new HalfCircleDegreeCoordinate(0,0,0);
        double expected = 0;
        double actual = zero.toRadians();
        Assertions.assertEquals(expected,actual,0.000000001);
    }

    @Test
    public void testToRadiansNorthPoleConvertsCorrectly() {
        HalfCircleDegreeCoordinate northPole = new HalfCircleDegreeCoordinate(90,0,0);
        double expected = Math.PI/2;
        double actual = northPole.toRadians();
        Assertions.assertEquals(expected,actual,0.000000001);
    }



    @Test
    public void testFromRadiansZeroConvertsCorrectly() {
        HalfCircleDegreeCoordinate actual = HalfCircleDegreeCoordinate.fromRadians(0);
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(0,0,0);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testFromRadiansRightAngleConvertsCorrectly() {
        HalfCircleDegreeCoordinate actual = HalfCircleDegreeCoordinate.fromRadians(Math.PI/2);
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(90,0,0);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testFromRadiansAboveNorthPoleThrowsException() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
                () -> HalfCircleDegreeCoordinate.fromRadians(Math.PI/2 + 0.000001)
        );
    }

    @Test
    public void testFromRadiansBelowSouthPoleThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> HalfCircleDegreeCoordinate.fromRadians(-Math.PI/2 - 0.000001)
        );
    }



    @Test
    public void testNegateZeroToZero() {
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(0,0,0);
        HalfCircleDegreeCoordinate actual = new HalfCircleDegreeCoordinate(0,0,0).negate();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testNegateNorthPoleToSouthPole() {
        HalfCircleDegreeCoordinate expected = new HalfCircleDegreeCoordinate(-90,0,0);
        HalfCircleDegreeCoordinate actual = new HalfCircleDegreeCoordinate(90,0,0).negate();
        Assertions.assertEquals(expected,actual);
    }
}
