package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FullCircleDegreeCoordinateTest {

    @Test
    public void testEqualsCoordinatesAreSameEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentDegreesNotEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(99, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentArcMinutesNotEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 45, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentArcSecondsNotEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsOtherTypeNotEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        String coordinate1 = "180, 30, 20.0";
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsNegationNotEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(-180, 30, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferenceBelowResolutionEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 30, 20.0001);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsModularEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(361, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(1, 30, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsNegationAndComplementEqual() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(-30, 0, 0.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(330, 0, 0.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testConstructorNegativeArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new FullCircleDegreeCoordinate(145, -30, 30.0)
        );
    }

    @Test
    public void testConstructorNegativeArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new FullCircleDegreeCoordinate(145, 30, -30.0)
        );
    }

    @Test
    public void testConstructorLargeArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new FullCircleDegreeCoordinate(145, 30, 60.0)
        );
    }

    @Test
    public void testConstructorLargeArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new FullCircleDegreeCoordinate(145, 60, 30.0)
        );
    }

    @Test
    public void testConstructorBarelyValidArcsecondsNoException() {
         new FullCircleDegreeCoordinate(145, 30, 59.9999999);
    }



    @Test
    public void testToRadiansZeroConvertsCorrectly() {
        FullCircleDegreeCoordinate zero = new FullCircleDegreeCoordinate(0,0,0);
        double expected = 0;
        double actual = zero.toRadians();
        Assertions.assertEquals(expected,actual,0.000000001);
    }

    @Test
    public void testToRadians90ConvertsCorrectly() {
        FullCircleDegreeCoordinate ninety = new FullCircleDegreeCoordinate(90,0,0);
        double expected = Math.PI/2;
        double actual = ninety.toRadians();
        Assertions.assertEquals(expected,actual,0.000000001);
    }



    @Test
    public void testFromRadiansZeroConvertsCorrectly() {
        double zero = 0.0;
        FullCircleDegreeCoordinate expected = new FullCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate actual = FullCircleDegreeCoordinate.fromRadians(zero);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFromRadiansRightAngleConvertsCorrectly() {
        double rightAngle = Math.PI/2;
        FullCircleDegreeCoordinate expected = new FullCircleDegreeCoordinate(90,0,0);
        FullCircleDegreeCoordinate actual = FullCircleDegreeCoordinate.fromRadians(rightAngle);
        Assertions.assertEquals(expected, actual);
    }
}
