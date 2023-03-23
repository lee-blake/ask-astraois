package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FullCircleDegreeCoordinatesTest {

    @Test
    public void testFullCircleDegreeCoordinatesAreTheSame() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testFullCircleDegreeCoordinatesWithDifferentDegrees() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(99, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testFullCircleDegreeCoordinateWithDifferentMinutes() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 45, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testFullCircleDegreeCoordinatesWithDifferentSeconds() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(180, 30, 30.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testFullCircleDegreeCoordinatesNonDegreeCoordinate() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(180, 30, 20.0);
        String coordinate1 = "180, 30, 20.0";
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testFullCircleDegreeCoordinatesNegationNotEqual() {
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
    public void testFullCircleDegreeCoordinatesEquals() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(361, 30, 20.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(1, 30, 20.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testFullCircleDegreeCoordinatesNegationAndComplement() {
        FullCircleDegreeCoordinate coordinate0 = new FullCircleDegreeCoordinate(-30, 0, 0.0);
        FullCircleDegreeCoordinate coordinate1 = new FullCircleDegreeCoordinate(330, 0, 0.0);
        boolean result = coordinate0.equals(coordinate1);
        Assertions.assertTrue(result);
    }
}
