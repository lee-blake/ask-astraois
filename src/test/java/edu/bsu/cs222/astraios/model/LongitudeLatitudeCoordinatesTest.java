package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LongitudeLatitudeCoordinatesTest {

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsDifferentTypeNotEqual() {
        LongitudeLatitudeCoordinates coordinates = new LongitudeLatitudeCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        String otherTypeObject = "";
        boolean result = coordinates.equals(otherTypeObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsBothSameEqual() {
        LongitudeLatitudeCoordinates coordinates0 = new LongitudeLatitudeCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        LongitudeLatitudeCoordinates coordinates1 = new LongitudeLatitudeCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsLatitudeDifferentNotEqual() {
        LongitudeLatitudeCoordinates coordinates0 = new LongitudeLatitudeCoordinates(
                new FullCircleDegreeCoordinate(90 , 0, 0),
                new HalfCircleDegreeCoordinate(5, 0, 0)
        );
        LongitudeLatitudeCoordinates coordinates1 = new LongitudeLatitudeCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsLongitudeDifferentNotEqual() {
        LongitudeLatitudeCoordinates coordinates0 = new LongitudeLatitudeCoordinates(
                new FullCircleDegreeCoordinate(89, 0, 0),
                new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        LongitudeLatitudeCoordinates coordinates1 = new LongitudeLatitudeCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertFalse(result);
    }
}
