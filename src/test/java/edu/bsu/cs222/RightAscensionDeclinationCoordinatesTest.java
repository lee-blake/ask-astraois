package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RightAscensionDeclinationCoordinatesTest {
    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonRightAscensionDeclinationsCoordinatesNotEqual() {
        HourCoordinate ra = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate dec = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords = new RightAscensionDeclinationCoordinates(ra,dec);
        boolean result = raDecCoords.equals("");
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameRaAndDecEqual() {
        HourCoordinate ra1 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate dec1 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(ra1,dec1);
        HourCoordinate ra2 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate dec2 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(ra2,dec2);
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsRightAscensionDifferentNotEqual() {
        HourCoordinate ra1 = new HourCoordinate(18, 30,30);
        HalfCircleDegreeCoordinate dec1 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(ra1,dec1);
        HourCoordinate ra2 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate dec2 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(ra2,dec2);
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDeclinationDifferentNotEqual() {
        HourCoordinate ra1 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate dec1 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(ra1,dec1);
        HourCoordinate ra2 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate dec2 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(ra2,dec2);
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDeclinationAndRightAscensionDifferentNotEqual() {
        HourCoordinate ra1 = new HourCoordinate(18, 30,30);
        HalfCircleDegreeCoordinate dec1 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(ra1,dec1);
        HourCoordinate ra2 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate dec2 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(ra2,dec2);
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }
}
