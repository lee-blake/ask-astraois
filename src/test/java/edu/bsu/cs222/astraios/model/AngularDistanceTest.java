package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AngularDistanceTest {

    @Test
    public void testConstructorNegativeDegreesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AngularDistance(-1,0,0)
        );
    }

    @Test
    public void testConstructorLargeDegreesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AngularDistance(181,0,0)
        );
    }

    @Test
    public void testConstructorNegativeArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AngularDistance(45,-1,0)
        );
    }

    @Test
    public void testConstructorLargeArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AngularDistance(45,60,0)
        );
    }

    @Test
    public void testConstructorNegativeArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AngularDistance(45,30,-1)
        );
    }

    @Test
    public void testConstructorLargeArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AngularDistance(45,30,60)
        );
    }

    @Test
    public void testConstructorBarelyValidArcsecondsNoException() {
        new AngularDistance(45,30,59.9999999999);
    }

    @Test
    public void testConstructor180NonzeroArcminutesThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AngularDistance(180,1,0)
        );
    }

    @Test
    public void testConstructor180NonzeroArcsecondsThrowsException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new AngularDistance(180,0,1)
        );
    }



    @Test
    public void testFromRadiansConstructsZeroCorrectly() {
        AngularDistance expected = new AngularDistance(0,0,0);
        AngularDistance actual = AngularDistance.fromRadians(0);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testFromRadiansConstructsRightAngleCorrectly() {
        double halfPi = Math.PI/2;
        AngularDistance expected = new AngularDistance(90,0,0);
        AngularDistance actual = AngularDistance.fromRadians(halfPi);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testFromRadiansNegativeRadiansThrowsExceptions() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> AngularDistance.fromRadians(-1)
        );
    }

    @Test
    public void testFromRadiansLargeRadiansThrowsExceptions() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> AngularDistance.fromRadians(Math.PI+0.00001)
        );
    }



    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsDifferentTypeNotEqual() {
        AngularDistance distance = new AngularDistance(45,30,20.0);
        String otherObject = "x";
        boolean result = distance.equals(otherObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameValueEqual() {
        AngularDistance distance1 = new AngularDistance(45,30,20.0);
        AngularDistance distance2 = new AngularDistance(45,30,20.0);
        boolean result = distance1.equals(distance2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentDegreesNotEqual() {
        AngularDistance distance1 = new AngularDistance(45,30,20.0);
        AngularDistance distance2 = new AngularDistance(46,30,20.0);
        boolean result = distance1.equals(distance2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentMinutesNotEqual() {
        AngularDistance distance1 = new AngularDistance(45,30,20.0);
        AngularDistance distance2 = new AngularDistance(45,29,20.0);
        boolean result = distance1.equals(distance2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentSecondsNotEqual() {
        AngularDistance distance1 = new AngularDistance(45,30,20.0);
        AngularDistance distance2 = new AngularDistance(45,30,21.0);
        boolean result = distance1.equals(distance2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferenceBelowResolutionEqual() {
        AngularDistance distance1 = new AngularDistance(45,30,20.0);
        AngularDistance distance2 = new AngularDistance(45,30,20.0001);
        boolean result = distance1.equals(distance2);
        Assertions.assertTrue(result);
    }



    @Test
    public void testLessThanZeroLessThanRightAngle() {
        AngularDistance zero = new AngularDistance(0,0,0);
        AngularDistance rightAngle = new AngularDistance(90,0,0);
        boolean result = zero.lessThan(rightAngle);
        Assertions.assertTrue(result);
    }

    @Test
    public void testLessThanRightAngleNotLessThanItself() {
        AngularDistance rightAngle1 = new AngularDistance(90,0,0);
        AngularDistance rightAngle2 = new AngularDistance(90,0,0);
        boolean result = rightAngle1.lessThan(rightAngle2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testLessThanRightAngleNotLessThanZero() {
        AngularDistance zero = new AngularDistance(0,0,0);
        AngularDistance rightAngle = new AngularDistance(90,0,0);
        boolean result = rightAngle.lessThan(zero);
        Assertions.assertFalse(result);
    }



    @Test
    public void testToStringFormatsZeroCorrectly() {
        String expected = "Δ{0°}";
        AngularDistance zero = new AngularDistance(0,0,0);
        String actual = zero.toString();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testToStringFormatsArcsecondCorrectly() {
        String expected = "Δ{0°0'1\"}";
        AngularDistance arcsecond = new AngularDistance(0,0,1);
        String actual = arcsecond.toString();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testToStringDropsSecondsCorrectly() {
        String expected = "Δ{2°30'}";
        AngularDistance noSeconds = new AngularDistance(2,30,0);
        String actual = noSeconds.toString();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testToStringDropsMinutesCorrect() {
        String expected = "Δ{17°}";
        AngularDistance noSeconds = new AngularDistance(17,0,0);
        String actual = noSeconds.toString();
        Assertions.assertEquals(expected,actual);
    }
}
