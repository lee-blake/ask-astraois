package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class ObservationTest {

    public void assertDifferenceLessThanOneSecond(HourCoordinate expected, HourCoordinate actual) {
        double expectedRadians = expected.toRadians();
        double actualRadians = actual.toRadians();
        double difference = Math.abs(expectedRadians - actualRadians);
        double oneSecondRadians = Math.PI/43200;
        String messageIfAssertFails = "Assertions failed, difference between expected '"
                + expected
                + "' and actual '"
                + actual
                + "' was not less than a second.";

        Assertions.assertTrue(difference < oneSecondRadians, messageIfAssertFails);
    }

    @Test
    public void testGetLocalSiderealTimeZeroEpochFixedValue() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(18, 41, 50.548);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeEpochPlusOneDayComputesCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-02T12:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(18, 45, 47.104);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeEpochPlusOneHourComputesCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T13:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(19, 42, 0.405);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeEpochPlusOneCenturyComputesCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2100-01-01T12:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(18, 44, 55.454);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeBallStateEpochComputesCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(13, 0, 12.402);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testGetLocalSiderealTimeBallState2023DateComputesCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2023-03-28T13:01:11Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(19, 42, 18.865);
        assertDifferenceLessThanOneSecond(expected, actual);
    }



    @Test
    public void testGetFractionalYearEpochZero() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2001-01-01T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetFractionalYearUTCOnePM1January2001() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2001-01-01T13:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / (365*24);
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCNoon2January2001() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2001-01-02T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / 365;
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCNoon2January2004() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2004-01-02T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / 366;
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCNoon2January2100() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2100-01-02T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / 365;
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCNoon2January2000() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2000-01-02T12:00:00Z")
        );
        double actual = observation.getFractionalYear();
        double expected = 2*Math.PI / 366;
        Assertions.assertEquals(expected, actual, 0.00000001);
    }

    @Test
    public void testGetFractionalYearUTCPlusOneOnePM1January2000() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildBallState(),
                OffsetDateTime.parse("2001-01-01T13:00:00+01:00")
        );
        double actual = observation.getFractionalYear();
        double expected = 0;
        Assertions.assertEquals(expected, actual);
    }
}
