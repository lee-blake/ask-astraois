package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

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
        String otherTypeObject = "x";
        boolean result = astronomicalObject.equals(otherTypeObject);
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

    @Test
    public void testGetAltAzAtObservationAtZeroZeroGetLstM13ComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                FullCircleDegreeCoordinate.fromRadians(5.3759725),
                HalfCircleDegreeCoordinate.fromRadians(-0.2701406)
        );
        Observation zeroZeroAndLSTZero = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T17:17:18Z")
        );
        AstronomicalObject m13 = TestObjectFactory.AstronomicalObjects.buildM13Object();
        AltitudeAzimuthCoordinates actual = m13.getAltAzAtObservation(zeroZeroAndLSTZero);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(new AngularDistance(1,0,0), error);
    }

    @Test
    public void testGetAltAzAtObservationAtZeroZeroGetLstM31ComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                FullCircleDegreeCoordinate.fromRadians(.213050104),
                HalfCircleDegreeCoordinate.fromRadians(.827764035)
        );
        Observation zeroZeroAndLSTZero = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T17:17:18Z")
        );
        AstronomicalObject m31 = TestObjectFactory.AstronomicalObjects.buildM31Object();
        AltitudeAzimuthCoordinates actual = m31.getAltAzAtObservation(zeroZeroAndLSTZero);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(new AngularDistance(1,0,0), error);
    }

    @Test
    public void testGetAltAzAtObservationAtZeroZeroGetLst1M13ComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(37,42,20.2),
                new HalfCircleDegreeCoordinate(-41,19,1.5)
        );
        Observation zeroZeroAndLSTZero = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(15,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T17:17:18Z")
        );
        AstronomicalObject m13 = TestObjectFactory.AstronomicalObjects.buildM13Object();
        AltitudeAzimuthCoordinates actual = m13.getAltAzAtObservation(zeroZeroAndLSTZero);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(new AngularDistance(1,0,0), error);
    }
}