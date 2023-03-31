package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class VisibilityCheckerTest {

    @Test
    public void testObjectIsVisibleIdealConditionsIsVisible() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T04:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "71 degrees above the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(12,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime,observation);
        boolean result = checker.objectIsVisible();
        Assertions.assertTrue(result);
    }

    @Test
    public void testObjectIsVisibleDaytimeNotVisible() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "doesn't matter, it's day",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime,observation);
        boolean result = checker.objectIsVisible();
        Assertions.assertFalse(result);
    }

    @Test
    public void testObjectIsVisibleBelowTheHorizonNotVisible() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T04:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "71 degrees below the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime,observation);
        boolean result = checker.objectIsVisible();
        Assertions.assertFalse(result);
    }

    @Test
    public void testObjectIsVisibleTwilightAboveTheHorizonIsVisible() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(0,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                ),
                OffsetDateTime.parse("2000-01-01T05:06:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "85 degrees above the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(12,0,0),
                        new HalfCircleDegreeCoordinate(0,0,0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime,observation);
        boolean result = checker.objectIsVisible();
        Assertions.assertTrue(result);
    }
}
