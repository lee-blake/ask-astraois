package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AstronomicalObjectTest {

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonAstronomicalObjectNotEqual() {
        HourCoordinate rightAscension = new HourCoordinate(18, 30, 30);
        HalfCircleDegreeCoordinate declination = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords = new RightAscensionDeclinationCoordinates(
                rightAscension,
                declination
        );
        String name = "myObject";
        AstronomicalObject astronomicalObject = new AstronomicalObject(name, raDecCoords);
        boolean result = astronomicalObject.equals(name);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsAllSameEqual() {
        HourCoordinate rightAscension1 = new HourCoordinate(18, 30, 30);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        String name1 = "myObject";
        AstronomicalObject astronomicalObject1 = new AstronomicalObject(name1, raDecCoords1);
        HourCoordinate rightAscension2 = new HourCoordinate(18, 30, 30);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        String name2 = "myObject";
        AstronomicalObject astronomicalObject2 = new AstronomicalObject(name2, raDecCoords2);
        boolean result = astronomicalObject1.equals(astronomicalObject2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsNameDifferentNotEqual() {
        HourCoordinate rightAscension1 = new HourCoordinate(18, 30, 30);
        HalfCircleDegreeCoordinate dec1 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                dec1
        );
        String name1 = "differentName";
        AstronomicalObject astronomicalObject1 = new AstronomicalObject(name1, raDecCoords1);
        HourCoordinate rightAscension2 = new HourCoordinate(18, 30, 30);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        String name2 = "myObject";
        AstronomicalObject astronomicalObject2 = new AstronomicalObject(name2, raDecCoords2);
        boolean result = astronomicalObject1.equals(astronomicalObject2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsRightAscensionDifferentNotEqual() {
        HourCoordinate rightAscension1 = new HourCoordinate(17, 30, 30);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        String name1 = "myObject";
        AstronomicalObject astronomicalObject1 = new AstronomicalObject(name1, raDecCoords1);
        HourCoordinate rightAscension2 = new HourCoordinate(18, 30, 30);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        String name2 = "myObject";
        AstronomicalObject astronomicalObject2 = new AstronomicalObject(name2, raDecCoords2);
        boolean result = astronomicalObject1.equals(astronomicalObject2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDeclinationDifferentNotEqual() {
        HourCoordinate rightAscension1 = new HourCoordinate(18, 30, 30);
        HalfCircleDegreeCoordinate declination1 = new HalfCircleDegreeCoordinate(43, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords1 = new RightAscensionDeclinationCoordinates(
                rightAscension1,
                declination1
        );
        String name1 = "myObject";
        AstronomicalObject astronomicalObject1 = new AstronomicalObject(name1, raDecCoords1);
        HourCoordinate rightAscension2 = new HourCoordinate(18, 30, 30);
        HalfCircleDegreeCoordinate declination2 = new HalfCircleDegreeCoordinate(44, 20, 17);
        RightAscensionDeclinationCoordinates raDecCoords2 = new RightAscensionDeclinationCoordinates(
                rightAscension2,
                declination2
        );
        String name2 = "myObject";
        AstronomicalObject astronomicalObject2 = new AstronomicalObject(name2, raDecCoords2);
        boolean result = astronomicalObject1.equals(astronomicalObject2);
        Assertions.assertFalse(result);
    }
}