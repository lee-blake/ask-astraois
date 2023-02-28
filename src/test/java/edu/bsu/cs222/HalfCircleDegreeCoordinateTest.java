package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HalfCircleDegreeCoordinateTest {

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonHalfCircleDegreeCoordinateNotEqual() {
        HalfCircleDegreeCoordinate coordinate = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        String randomObject = "";
        boolean result = coordinate.equals(randomObject);
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
    public void testEqualsDifferenceIsModulusEqual() {
        HalfCircleDegreeCoordinate coordinate0 = new HalfCircleDegreeCoordinate(45, 30, 30.0);
        HalfCircleDegreeCoordinate coordinate1 = new HalfCircleDegreeCoordinate(45+180, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsNegationAndComplementAreEqual() {
        HalfCircleDegreeCoordinate negation = new HalfCircleDegreeCoordinate(-45, 20, 20.0);
        HalfCircleDegreeCoordinate complement = new HalfCircleDegreeCoordinate(134, 39, 40.0);
        boolean result = negation.equals(complement);
        Assertions.assertTrue(result);
    }
}
