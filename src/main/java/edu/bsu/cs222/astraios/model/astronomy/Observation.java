package edu.bsu.cs222.astraios.model.astronomy;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
        // By storing internally as UTC, we drastically simplify much of the conversion math.
        // Changing this is a very bad idea.
        this.observationTime = time.withOffsetSameInstant(ZoneOffset.UTC);
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

    public double getFractionalYear() {
        OffsetDateTime zeroOfYear = EPOCH.withYear(this.observationTime.getYear());
        long secondsElapsed = Duration.between(zeroOfYear, this.observationTime).getSeconds();
        double fractionalDaysElapsed = secondsElapsed / SECONDS_PER_DAY;
        int daysInThisYear = getDaysInThisYear();
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

    public double getSolarHourAngleInRadians(double equationOfTimeInRadians) {
        double offset = this.longitudeLatitude.getLongitudeAsRadians() + equationOfTimeInRadians;
        int hour = this.observationTime.getHour();
        int minute = this.observationTime.getMinute();
        int second = this.observationTime.getSecond();
        double fractionalHours = hour + minute/60.0 + second/3600.0;
        double trueSolarTimeRadians = Math.PI*fractionalHours*15/180 + offset;
        return trueSolarTimeRadians - Math.PI;
    }

    public double getLatitudeAsRadians() {
        return this.longitudeLatitude.getLatitudeAsRadians();
    }
}
