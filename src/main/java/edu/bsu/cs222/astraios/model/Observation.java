package edu.bsu.cs222.astraios.model;

import java.time.Duration;
import java.time.OffsetDateTime;

public class Observation {

    private static final OffsetDateTime EPOCH = OffsetDateTime.parse("2000-01-01T12:00:00Z");
    private static final HourCoordinate LOCAL_SIDEREAL_TIME_AT_EPOCH = new HourCoordinate(18,41,50.548);
    private static final double RADIANS_DRIFT_PER_DAY_AFTER_EPOCH =  0.0172027925;
    private static final double SECONDS_PER_DAY = 86400.0;
    private static final HourCoordinate NOON = new HourCoordinate(12,0,0);

    private final OffsetDateTime observationTime;
    private final LongitudeLatitudeCoordinates longitudeLatitude;

    public Observation(LongitudeLatitudeCoordinates longitudeLatitudeCoordinates, OffsetDateTime time) {
        this.longitudeLatitude = longitudeLatitudeCoordinates;
        this.observationTime = time;
    }

    public HourCoordinate getLocalSiderealTime() {
        Duration timeDifference = Duration.between(EPOCH, observationTime);
        double daysElapsedSinceEpoch = timeDifference.getSeconds() / SECONDS_PER_DAY;
        HourCoordinate timeCoordinate = this.getTimeAsHourCoordinate();
        double localSiderealTimeAsRadians = LOCAL_SIDEREAL_TIME_AT_EPOCH.toRadians()
                + daysElapsedSinceEpoch*RADIANS_DRIFT_PER_DAY_AFTER_EPOCH
                + timeCoordinate.toRadians() - NOON.toRadians()
                + longitudeLatitude.getLongitudeAsRadians();
        return HourCoordinate.fromRadians(localSiderealTimeAsRadians);
    }

    private HourCoordinate getTimeAsHourCoordinate() {
        return new HourCoordinate(
                this.observationTime.getHour(),
                this.observationTime.getMinute(),
                this.observationTime.getSecond()
        );
    }

    public double getLatitudeAsRadians() {
        return this.longitudeLatitude.getLatitudeAsRadians();
    }

    public double getFractionalYear() {
        OffsetDateTime zeroOfYear = EPOCH.withYear(this.observationTime.getYear());
        long secondsElapsed = Duration.between(zeroOfYear, this.observationTime).getSeconds();
        int daysInThisYear = getDaysInThisYear();
        double fractionalDaysElapsed = secondsElapsed / SECONDS_PER_DAY;
        return 2*Math.PI*fractionalDaysElapsed / daysInThisYear;
    }

    private int getDaysInThisYear() {
        int year = this.observationTime.getYear();
        boolean isLeapYear = (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
        if(isLeapYear) {
            return 366;
        }
        else {
            return 365;
        }
    }
}
