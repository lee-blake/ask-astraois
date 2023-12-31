package edu.bsu.cs222.astraios.model.astronomy;

import edu.bsu.cs222.astraios.CustomAssertions;
import edu.bsu.cs222.astraios.TestObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class SunTest {

    @Test
    public void testGetLocationDuringObservationZeroZeroEpochComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
                new FullCircleDegreeCoordinate(178, 3, 34.38),
                new HalfCircleDegreeCoordinate(66, 57, 37.3356)
        );
        Observation zeroZeroEpoch = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        Sun sun = new Sun();
        AzimuthAltitudeCoordinates actual = sun.getLocationDuringObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(1, 0, 0),
                error
        );
    }

    @Test
    public void testGetLocationDuringObservationZeroZeroEpochPlus1hourComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
                new FullCircleDegreeCoordinate(209, 56, 13.344),
                new HalfCircleDegreeCoordinate(63, 10, 20.9928)
        );
        Observation zeroZeroEpoch = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T13:00:00Z")
        );
        Sun sun = new Sun();
        AzimuthAltitudeCoordinates actual = sun.getLocationDuringObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(1, 0, 0),
                error
        );
    }

    @Test
    public void testGetLocationDuringObservationNOAASpreadsheetDefaultLocationUTC6_06ComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
                new FullCircleDegreeCoordinate(345, 52, 8.7682),
                new HalfCircleDegreeCoordinate(-25, 14, 44.5866)
        );
        Observation noaaDefaultUTC = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(-105, 0, 0),
                        new HalfCircleDegreeCoordinate(40, 0, 0)
                ),
                OffsetDateTime.parse("2010-06-21T06:06:00Z")
        );
        Sun sun = new Sun();
        AzimuthAltitudeCoordinates actual = sun.getLocationDuringObservation(noaaDefaultUTC);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(1, 0, 0),
                error
        );
    }

    @Test
    public void testGetLocationDuringObservationNOAASpreadsheetDefaultLocationLocal0_06ComputesCorrectly() {
        AzimuthAltitudeCoordinates expected = new AzimuthAltitudeCoordinates(
                new FullCircleDegreeCoordinate(345, 52, 8.7682),
                new HalfCircleDegreeCoordinate(-25, 14, 44.5866)
        );
        Observation noaaDefaultOffset = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(-105, 0, 0),
                        new HalfCircleDegreeCoordinate(40, 0, 0)
                ),
                OffsetDateTime.parse("2010-06-21T00:06:00-06:00")
        );
        Sun sun = new Sun();
        AzimuthAltitudeCoordinates actual = sun.getLocationDuringObservation(noaaDefaultOffset);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(
                new AngularDistance(1, 0, 0),
                error
        );
    }



    @Test
    public void testIsNightAround17DegreesBelowNotNight() {
        Observation observation = new Observation(
            TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
            OffsetDateTime.parse("2000-01-01T04:50:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.isNight(observation);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsNightAround19DegreesBelowIsNight() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:40:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.isNight(observation);
        Assertions.assertTrue(result);
    }



    @Test
    public void testIsAstronomicalTwilightAround17DegreesBelowIsTwilight() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:50:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.isAstronomicalTwilight(observation);
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsAstronomicalTwilightAround19DegreesBelowNotTwilight() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:40:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.isAstronomicalTwilight(observation);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsAstronomicalTwilightAround11DegreesBelowNotTwilight() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T05:13:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.isAstronomicalTwilight(observation);
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsAstronomicalTwilightAround13DegreesBelowIsTwilight() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T05:06:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.isAstronomicalTwilight(observation);
        Assertions.assertTrue(result);
    }



    @Test
    public void testSunInterferesWithViewingAround11DegreesSunInterferes() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T05:13:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.sunInterferesWithViewing(observation);
        Assertions.assertTrue(result);
    }

    @Test
    public void testSunInterferesWithViewingAround13DegreesSunNoInterference() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T05:06:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.sunInterferesWithViewing(observation);
        Assertions.assertFalse(result);
    }

    @Test
    public void testSunInterferesWithViewingAround19DegreesSunNoInterference() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:40:00Z")
        );
        Sun sun = new Sun();
        boolean result = sun.sunInterferesWithViewing(observation);
        Assertions.assertFalse(result);
    }
}
