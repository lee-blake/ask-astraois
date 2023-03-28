import edu.bsu.cs222.astraios.model.FullCircleDegreeCoordinate;
import edu.bsu.cs222.astraios.model.HalfCircleDegreeCoordinate;
import edu.bsu.cs222.astraios.model.HourCoordinate;
import edu.bsu.cs222.astraios.model.LongitudeLatitudeCoordinates;
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
        HourCoordinate expected = new HourCoordinate(18,41,50.4);
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
        HourCoordinate expected = new HourCoordinate(18,45,46.955);
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
        HourCoordinate expected = new HourCoordinate(19,42,0.2565);
        assertDifferenceLessThanOneSecond(expected, actual);
    }

    @Test
    public void testBallStateRecentComputesCorrectly() {
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        new FullCircleDegreeCoordinate(-85,24,32.2),
                        new HalfCircleDegreeCoordinate(40,11,53.96)
                ),
                OffsetDateTime.parse("2023-03-28T13:13:24Z")
        );
        HourCoordinate actual = observation.getLocalSiderealTime();
        HourCoordinate expected = new HourCoordinate(19,54,33);
        assertDifferenceLessThanOneSecond(expected, actual);
    }
}
