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
        AstronomicalObject astronomicalObject = new AstronomicalObject(
                "myObject",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(18, 30, 30),
                        new HalfCircleDegreeCoordinate(44, 20, 17)
                )
        );
        String otherTypeObject = "x";
        boolean result = astronomicalObject.equals(otherTypeObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsAllSameEqual() {
        AstronomicalObject astronomicalObject1 = new AstronomicalObject(
                "myObject",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(18, 30, 30),
                        new HalfCircleDegreeCoordinate(44, 20, 17)
                )
        );
        AstronomicalObject astronomicalObject2 = new AstronomicalObject(
                "myObject",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(18, 30, 30),
                        new HalfCircleDegreeCoordinate(44, 20, 17)
                )
        );
        boolean result = astronomicalObject1.equals(astronomicalObject2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsNameDifferentNotEqual() {
        AstronomicalObject astronomicalObject1 = new AstronomicalObject(
                "differentName",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(18, 30, 30),
                        new HalfCircleDegreeCoordinate(44, 20, 17)
                )
        );
        AstronomicalObject astronomicalObject2 = new AstronomicalObject(
                "myObject",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(18, 30, 30),
                        new HalfCircleDegreeCoordinate(44, 20, 17)
                )
        );
        boolean result = astronomicalObject1.equals(astronomicalObject2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsRightAscensionDifferentNotEqual() {
        AstronomicalObject astronomicalObject1 = new AstronomicalObject(
                "myObject",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(17, 30, 30),
                        new HalfCircleDegreeCoordinate(44, 20, 17)
                )
        );
        AstronomicalObject astronomicalObject2 = new AstronomicalObject(
                "myObject",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(18, 30, 30),
                        new HalfCircleDegreeCoordinate(44, 20, 17)
                )
        );
        boolean result = astronomicalObject1.equals(astronomicalObject2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDeclinationDifferentNotEqual() {
        AstronomicalObject astronomicalObject1 = new AstronomicalObject(
                "myObject",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(18, 30, 30),
                        new HalfCircleDegreeCoordinate(43, 20, 17)
                )
        );
        AstronomicalObject astronomicalObject2 = new AstronomicalObject(
                "myObject",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(18, 30, 30),
                        new HalfCircleDegreeCoordinate(44, 20, 17)
                )
        );
        boolean result = astronomicalObject1.equals(astronomicalObject2);
        Assertions.assertFalse(result);
    }



    @Test
    public void testGetAltAzAtObservationZeroZeroEpochZeroZeroComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(10, 27, 38.25)
        );
        Observation zeroZeroEpoch = TestObjectFactory.Observations.buildZeroZeroEpoch();
        AstronomicalObject zeroZero = new AstronomicalObject(
                "zeroZero",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        AzimuthAltitudeCoordinates actual = zeroZero.getAltAzAtObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationZeroZeroEpochOneZeroComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(-4, 32, 21.76)
        );
        Observation zeroZeroEpoch = TestObjectFactory.Observations.buildZeroZeroEpoch();
        AstronomicalObject oneZero = new AstronomicalObject(
                "oneZero",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(1, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        AzimuthAltitudeCoordinates actual = oneZero.getAltAzAtObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationMinusFifteenZeroEpochZeroZeroComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
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
        AzimuthAltitudeCoordinates actual = zeroZero.getAltAzAtObservation(oneHourWestOfZeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationZeroZeroEpochNorthCelestialPoleComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
                new FullCircleDegreeCoordinate(0, 0, 0),
                new HalfCircleDegreeCoordinate(0, 0, 0)
        );
        Observation zeroZeroEpoch = TestObjectFactory.Observations.buildZeroZeroEpoch();
        AstronomicalObject northCelestialPole = new AstronomicalObject(
                "north celestial pole",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(90, 0, 0)
                )
        );
        AzimuthAltitudeCoordinates actual = northCelestialPole.getAltAzAtObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationNorthPoleEpochNorthCelestialPoleComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
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
        AzimuthAltitudeCoordinates actual = northCelestialPole.getAltAzAtObservation(northPoleEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }

    @Test
    public void testGetAltAzAtObservationZeroZeroEpochZeroZeroNonUTCComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
                new FullCircleDegreeCoordinate(90, 0, 0),
                new HalfCircleDegreeCoordinate(10, 27, 38.25)
        );
        Observation zeroZeroAndLSTZero = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T13:00:00+01:00")
        );
        AstronomicalObject zeroZero = new AstronomicalObject(
                "zeroZero",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        AzimuthAltitudeCoordinates actual = zeroZero.getAltAzAtObservation(zeroZeroAndLSTZero);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(0, 1, 0),
                error
        );
    }
}