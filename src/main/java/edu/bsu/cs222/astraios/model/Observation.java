package edu.bsu.cs222.astraios.model;

import java.time.Duration;
import java.time.OffsetDateTime;

public class Observation {

    private static final HourCoordinate LOCAL_SIDEREAL_TIME_AT_EPOCH = new HourCoordinate(18,41,50.548);
    private static final double RADIANS_SHIFT_PER_DAY =  0.0172027925;

    private final OffsetDateTime time;
    private final LongitudeLatitudeCoordinates longitudeLatitude;

    public Observation(LongitudeLatitudeCoordinates longitudeLatitudeCoordinates, OffsetDateTime time) {
        this.longitudeLatitude = longitudeLatitudeCoordinates;
        this.time = time;
    }

    public HourCoordinate getLocalSiderealTime() {
        OffsetDateTime epoch = OffsetDateTime.parse("2000-01-01T12:00:00Z");
        Duration difference = Duration.between(epoch, time);
        double daysElapsedSinceEpoch = difference.getSeconds() / 86400.0;
        HourCoordinate timeCoordinate = new HourCoordinate(
                this.time.getHour(),
                this.time.getMinute(),
                this.time.getSecond()
        );
        HourCoordinate noon = new HourCoordinate(12,0,0);
        double localSiderealTimeAsRadians = LOCAL_SIDEREAL_TIME_AT_EPOCH.toRadians()
                + daysElapsedSinceEpoch * RADIANS_SHIFT_PER_DAY
                + timeCoordinate.toRadians() - noon.toRadians()
                + longitudeLatitude.getLongitude().toRadians();
        return HourCoordinate.fromRadians(localSiderealTimeAsRadians);
    }
}
