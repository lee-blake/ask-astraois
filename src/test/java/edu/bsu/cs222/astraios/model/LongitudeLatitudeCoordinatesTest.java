package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LongitudeLatitudeCoordinatesTest {

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsDifferentTypeNotEqual() {
        HalfCircleDegreeCoordinate latitude = new HalfCircleDegreeCoordinate(0, 0, 0);
        FullCircleDegreeCoordinate longitude = new FullCircleDegreeCoordinate(90, 0, 0);
        LongitudeLatitudeCoordinates coordinates = new LongitudeLatitudeCoordinates(longitude, latitude);
        String otherTypeObject = "";
        boolean result = coordinates.equals(otherTypeObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsBothSameEqual() {
        HalfCircleDegreeCoordinate latitude0 = new HalfCircleDegreeCoordinate(0, 0, 0);
        FullCircleDegreeCoordinate longitude0 = new FullCircleDegreeCoordinate(90, 0, 0);
        LongitudeLatitudeCoordinates coordinates0 = new LongitudeLatitudeCoordinates(longitude0, latitude0);
        HalfCircleDegreeCoordinate latitude1 = new HalfCircleDegreeCoordinate(0, 0, 0);
        FullCircleDegreeCoordinate longitude1 = new FullCircleDegreeCoordinate(90, 0, 0);
        LongitudeLatitudeCoordinates coordinates1 = new LongitudeLatitudeCoordinates(longitude1, latitude1);
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsLatitudeDifferentNotEqual() {
        HalfCircleDegreeCoordinate latitude0 = new HalfCircleDegreeCoordinate(5, 0, 0);
        FullCircleDegreeCoordinate longitude0 = new FullCircleDegreeCoordinate(90 , 0, 0);
        LongitudeLatitudeCoordinates coordinates0 = new LongitudeLatitudeCoordinates(longitude0, latitude0);
        HalfCircleDegreeCoordinate latitude1 = new HalfCircleDegreeCoordinate(0, 0, 0);
        FullCircleDegreeCoordinate longitude1 = new FullCircleDegreeCoordinate(90, 0, 0);
        LongitudeLatitudeCoordinates coordinates1 = new LongitudeLatitudeCoordinates(longitude1, latitude1);
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsLongitudeDifferentNotEqual() {
        HalfCircleDegreeCoordinate latitude0 = new HalfCircleDegreeCoordinate(0, 0, 0);
        FullCircleDegreeCoordinate longitude0 = new FullCircleDegreeCoordinate(89, 0, 0);
        LongitudeLatitudeCoordinates coordinates0 = new LongitudeLatitudeCoordinates(longitude0, latitude0);
        HalfCircleDegreeCoordinate latitude1 = new HalfCircleDegreeCoordinate(0, 0, 0);
        FullCircleDegreeCoordinate longitude1 = new FullCircleDegreeCoordinate(90, 0, 0);
        LongitudeLatitudeCoordinates coordinates1 = new LongitudeLatitudeCoordinates(longitude1, latitude1);
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertFalse(result);
    }
}
