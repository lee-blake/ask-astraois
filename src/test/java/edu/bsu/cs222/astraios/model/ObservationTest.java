package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class ObservationTest {

    public void assertDifferenceLessThanOneSecond(HourCoordinate expected, HourCoordinate actual) {
        double expectedRadians = expected.toRadians();
        double actualRadians = actual.toRadians();
        double difference = Math.abs(expectedRadians - actualRadians);
        double oneSecond = Math.PI/43200;
        String messageIfAssertFails = "Assertions failed, difference between expected '"
                + expected
                + "' and actual '"
                + actual
                + "' was not less than a second.";

        Assertions.assertTrue(difference < oneSecond, messageIfAssertFails);
    }

    @Test
    public void testGetLocalSiderealTimeZeroEpochFixedValue() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(18,41,50.548);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeEpochPlusOneDayComputesCorrectly() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-02T12:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(18,45,47.104);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeEpochPlusOneHourComputesCorrectly() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T13:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(19,42,00.405);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeEpochPlusOneCenturyComputesCorrectly() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2100-01-01T12:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(18,44,55.454);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeBallStateEpochComputesCorrectly() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(-85,24,32.2),
                        new HalfCircleDegreeCoordinate(40,11,53.96)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(13,0,12.402);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeBallState2023DateComputesCorrectly() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(-85,24,32.2),
                        new HalfCircleDegreeCoordinate(40,11,53.96)
                ),
                OffsetDateTime.parse("2023-03-28T13:01:11Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(19,42,18.865);
        assertDifferenceLessThanOneSecond(expected, actual);
    }



    @Test
    public void testGetFractionalYearEpochZero() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitude.buildBallState(),
                OffsetDateTime.parse("2001-01-01T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetFractionalYearUTCOnePM1January2001() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitude.buildBallState(),
                OffsetDateTime.parse("2001-01-01T13:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / (365*24);
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCNoon2January2001() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitude.buildBallState(),
                OffsetDateTime.parse("2001-01-02T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / 365;
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCNoon2January2004() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitude.buildBallState(),
                OffsetDateTime.parse("2004-01-02T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / 366;
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCNoon2January2100() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitude.buildBallState(),
                OffsetDateTime.parse("2100-01-02T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / 365;
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCNoon2January2000() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitude.buildBallState(),
                OffsetDateTime.parse("2000-01-02T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / 366;
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCPlusOneOnePM1January2000() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitude.buildBallState(),
                OffsetDateTime.parse("2001-01-01T13:00:00+01:00")
        );
        double actual = observation.getFractionalYear();
        double expected = 0;
        Assertions.assertEquals(expected, actual);
    }
}
