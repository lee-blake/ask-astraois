package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class SunTest {

    @Test
    public void testGetLocationDuringObservationZeroZeroEpochComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(178,3,34.38),
                new HalfCircleDegreeCoordinate(66,57,37.3356)
        );
        Observation zeroZeroEpoch = new Observation(
                new LongitudeLatitudeCoordinates(
                new FullCircleDegreeCoordinate(0,0,0),
                new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        Sun sun = new Sun();
        AltitudeAzimuthCoordinates actual = sun.getLocationDuringObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(new AngularDistance(1,0,0),error);
    }

    @Test
    public void testGetLocationDuringObservationZeroZeroEpochPlus1hourComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(209,56,13.344),
                new HalfCircleDegreeCoordinate(63,10,20.9928)
        );
        Observation zeroZeroEpoch = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T13:00:00Z")
        );
        Sun sun = new Sun();
        AltitudeAzimuthCoordinates actual = sun.getLocationDuringObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(new AngularDistance(1,0,0),error);
    }

    @Test
    public void testGetLocationDuringObservationNOAASpreadsheetDefaultLocationUTC6_06ComputesCorrectly() {
        AltitudeAzimuthCoordinates expected = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(345,52,8.7682),
                new HalfCircleDegreeCoordinate(-25,14,44.5866)
        );
        Observation zeroZeroEpoch = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(-105,0,0),
                        new HalfCircleDegreeCoordinate(40,0,0)
                ),
                OffsetDateTime.parse("2010-06-21T06:06:00Z")
        );
        Sun sun = new Sun();
        AltitudeAzimuthCoordinates actual = sun.getLocationDuringObservation(zeroZeroEpoch);
        AngularDistance error = expected.distanceTo(actual);
        CustomAssertions.assertBounded(new AngularDistance(1,0,0),error);
    }
}
