package edu.bsu.cs222.astraios.model.astronomy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RightAscensionDeclinationCoordinatesTest {

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonRightAscensionDeclinationsCoordinatesNotEqual() {
        RightAscensionDeclinationCoordinates raDecCoords = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(17, 30, 30),
                new HalfCircleDegreeCoordinate(43, 20, 17)
        );
        boolean result = raDecCoords.equals("");
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameRightAscensionAndDeclinationEqual() {
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(17, 30, 30),
                new HalfCircleDegreeCoordinate(43, 20, 17)
        );
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(17, 30, 30),
                new HalfCircleDegreeCoordinate(43, 20, 17)
        );
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsRightAscensionDifferentNotEqual() {
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(18, 30, 30),
                new HalfCircleDegreeCoordinate(43, 20, 17)
        );
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(17, 30, 30),
                new HalfCircleDegreeCoordinate(43, 20, 17)
        );
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDeclinationDifferentNotEqual() {
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(17, 30, 30),
                new HalfCircleDegreeCoordinate(44, 20, 17)
        );
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(17, 30, 30),
                new HalfCircleDegreeCoordinate(43, 20, 17)
        );
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDeclinationAndRightAscensionDifferentNotEqual() {
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(18, 30, 30),
                new HalfCircleDegreeCoordinate(44, 20, 17)
        );
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                new HourCoordinate(17, 30, 30),
                new HalfCircleDegreeCoordinate(43, 20, 17)
        );
        boolean result = raDecCoords1.equals(raDecCoords2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEditRightAscensionOriginalRightAscensionDoesNotChange() {
        HourCoordinate rightAscension1 = new HourCoordinate(13, 37, 00.9);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(-29, 51, 57);
        RightAscensionDeclinationCoordinates expected = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        HourCoordinate rightAscension2 = new HourCoordinate(13, 37, 00.9);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(-29, 51, 57);
        RightAscensionDeclinationCoordinates actual = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        actual.editRightAscension(new HourCoordinate(13, 37, 00.9));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEditRightAscensionDifferentRightAscensionDoesChange() {
        HourCoordinate rightAscension1 = new HourCoordinate(14, 37, 00.9);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(-29, 51, 57);
        RightAscensionDeclinationCoordinates expected = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        HourCoordinate rightAscension2 = new HourCoordinate(13, 37, 00.9);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(-29, 51, 57);
        RightAscensionDeclinationCoordinates actual = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        actual.editRightAscension(new HourCoordinate(14, 37, 00.9));
        Assertions.assertEquals(expected, actual);
    }



    @Test
    public void testEditDeclinationOriginalDeclinationDoesNotChange() {
        HourCoordinate rightAscension1 = new HourCoordinate(21, 42, 44.0);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(40, 25, 10.0);
        RightAscensionDeclinationCoordinates expected = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        HourCoordinate rightAscension2 = new HourCoordinate(21, 42, 44.0);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(40, 25, 10.0);
        RightAscensionDeclinationCoordinates actual = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        actual.editDeclination(new HalfCircleDegreeCoordinate(40, 25, 10.0));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEditDeclinationDifferentDeclinationDoesChange() {
        HourCoordinate rightAscension1 = new HourCoordinate(21, 42, 44.0);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(40, 25, 10.0);
        RightAscensionDeclinationCoordinates expected = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        HourCoordinate rightAscension2 = new HourCoordinate(21, 42, 44.0);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(42, 20, 17.0);
        RightAscensionDeclinationCoordinates actual = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        actual.editDeclination(new HalfCircleDegreeCoordinate(40, 25, 10.0));
        Assertions.assertEquals(expected, actual);
    }
}
