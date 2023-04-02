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
    public void testGetAltAzAtObservationZeroZeroEpochZeroZeroComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(10, 27, 38.25)
        );
        Observation zeroZeroEpoch = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject zeroZero = new AstronomicalObject(
                "zeroZero",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        AltitudeAzimuthCoordinates actual = zeroZero.getAltAzAtObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationZeroZeroEpochOneZeroComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(-4, 32, 21.76)
        );
        Observation oneHourWestOfZeroZeroEpoch = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject oneZero = new AstronomicalObject(
                "oneZero",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(1, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        AltitudeAzimuthCoordinates actual = oneZero.getAltAzAtObservation(oneHourWestOfZeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationMinusFifteenZeroEpochZeroZeroComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(-4, 32, 21.76)
        );
        Observation oneHourWestOfZeroZeroEpoch = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(-15, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject zeroZero = new AstronomicalObject(
                "zeroZero",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        AltitudeAzimuthCoordinates actual = zeroZero.getAltAzAtObservation(oneHourWestOfZeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationZeroZeroEpochNorthCelestialPoleComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(0, 0, 0),
                new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        Observation zeroZeroEpoch = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject northCelestialPole = new AstronomicalObject(
                "north celestial pole",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(90, 0, 0)
                )
        );
        AltitudeAzimuthCoordinates actual = northCelestialPole.getAltAzAtObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationNorthPoleEpochNorthCelestialPoleComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(0, 0, 0),
                new HalfCircleDegreeCoordinate(90, 0, 0)
        );
        Observation northPoleEpoch = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(90, 0, 0)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject northCelestialPole = new AstronomicalObject(
                "north celestial pole",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(90, 0, 0)
                )
        );
        AltitudeAzimuthCoordinates actual = northCelestialPole.getAltAzAtObservation(northPoleEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationZeroZeroEpochZeroZeroNonUTCComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(10, 27, 38.25)
        );
        Observation zeroZeroAndLSTZero = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                ),
                OffsetDateTime.parse("2000-01-01T13:00:00+01:00")
        );
        AstronomicalObject zeroZero = new AstronomicalObject(
                "zeroZero",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        AltitudeAzimuthCoordinates actual = zeroZero.getAltAzAtObservation(zeroZeroAndLSTZero);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }
}