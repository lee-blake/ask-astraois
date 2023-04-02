package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class VisibilityCheckerTest {

    @Test
    public void testObjectIsVisibleIdealConditionsIsVisible() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "71 degrees above the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(12, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime, observation);
        boolean result = checker.objectIsVisible();
        Assertions.assertTrue(result);
    }

    @Test
    public void testObjectIsVisibleDaytimeNotVisible() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "doesn't matter, it's day",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime, observation);
        boolean result = checker.objectIsVisible();
        Assertions.assertFalse(result);
    }

    @Test
    public void testObjectIsVisibleBelowTheHorizonNotVisible() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:00:00Z")
        );
        AstronomicalObject objectBelowHorizonAtTime = new AstronomicalObject(
                "71 degrees below the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectBelowHorizonAtTime, observation);
        boolean result = checker.objectIsVisible();
        Assertions.assertFalse(result);
    }

    @Test
    public void testObjectIsVisibleTwilightAboveTheHorizonIsVisible() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T05:06:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "85 degrees above the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(12, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime, observation);
        boolean result = checker.objectIsVisible();
        Assertions.assertTrue(result);
    }



    @Test
    public void testBuildVisibilityStatusStringSunInterferesBuildsCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "above the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime, observation);
        String actual = checker.buildVisibilityStatusString();
        String expected = """
                The object 'above the horizon' is not visible due to the following reasons:
                -It is neither night nor astronomical twilight.""";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBuildVisibilityStatusStringBelowTheHorizonBuildsCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:00:00Z")
        );
        AstronomicalObject objectBelowHorizonAtTime = new AstronomicalObject(
                "71 degrees below the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(0, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectBelowHorizonAtTime, observation);
        String actual = checker.buildVisibilityStatusString();
        String expected = """
                The object '71 degrees below the horizon' is not visible due to the following reasons:
                -The object is below the horizon.""";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBuildVisibilityStatusStringMultipleReasonsBuildsCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T12:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "below the horizon",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(12, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime, observation);
        String actual = checker.buildVisibilityStatusString();
        String expected = """
                The object 'below the horizon' is not visible due to the following reasons:
                -It is neither night nor astronomical twilight.
                -The object is below the horizon.""";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBuildVisibilityStatusStringVisibleObjectTwelveBuildsCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "testObject12",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(12, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime, observation);
        String actual = checker.buildVisibilityStatusString();
        String expected = """                
                Name              R.A.         Dec.         Az.          Alt.     \s
                testObject12      12h00m00.0s   +00°00'00.0"   90°00'00"    +70°07'55.4\"""";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBuildVisibilityStatusStringVisibleObjectElevenBuildsCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T04:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "testObject11",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(11, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime, observation);
        String actual = checker.buildVisibilityStatusString();
        String expected = """         
                Name              R.A.         Dec.         Az.          Alt.     \s
                testObject11      11h00m00.0s   +00°00'00.0"   90°00'00"    +85°07'55.4\"""";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testBuildVisibilityStatusStringTwilightBuildsCorrectly() {
        Observation observation = new Observation(
                TestObjectFactory.LongitudeLatitudes.buildZeroZero(),
                OffsetDateTime.parse("2000-01-01T05:00:00Z")
        );
        AstronomicalObject objectAboveHorizonAtTime = new AstronomicalObject(
                "testObject11",
                new RightAscensionDeclinationCoordinates(
                        new HourCoordinate(11, 0, 0),
                        new HalfCircleDegreeCoordinate(0, 0, 0)
                )
        );
        VisibilityChecker checker = new VisibilityChecker(objectAboveHorizonAtTime, observation);
        String actual = checker.buildVisibilityStatusString();
        String expected = """
                Warning: This observation is in astronomical twilight, which may interfere with viewing faint objects.
                Name              R.A.         Dec.         Az.          Alt.     \s
                testObject11      11h00m00.0s   +00°00'00.0"   270°00'00"   +79°49'36.7\"""";
        Assertions.assertEquals(expected, actual);
    }
}
