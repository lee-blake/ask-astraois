package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RightAscensionDeclinationCoordinatesTest {

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonRightAscensionDeclinationsCoordinatesNotEqual() {
        HourCoordinate rightAscension = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate declination = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords = new RightAscensionDeclinationCoordinates(
                rightAscension,
                declination
        );
        boolean result = raDecCoords.equals("");
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameRightAscensionAndDeclinationEqual() {
        HourCoordinate rightAscension1 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        HourCoordinate rightAscension2 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsRightAscensionDifferentNotEqual() {
        HourCoordinate rightAscension1 = new HourCoordinate(18, 30,30);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        HourCoordinate rightAscension2 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDeclinationDifferentNotEqual() {
        HourCoordinate rightAscension1 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        HourCoordinate rightAscension2 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDeclinationAndRightAscensionDifferentNotEqual() {
        HourCoordinate rightAscension1 = new HourCoordinate(18, 30,30);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        HourCoordinate rightAscension2 = new HourCoordinate(17, 30,30);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }
}
