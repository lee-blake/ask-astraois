package edu.bsu.cs222;

public class HourCoordinate {

    // Resolution goes down to 1/15 of a millisecond
    // This translates to 1/1000 of an arcminute in degrees.
    // This should be adequate for this project.
    private static final long MAX_UNITS = 24*60*60*15000;
    private static final long UNITS_PER_HOUR = 60*60*15000;
    private static final long UNITS_PER_MINUTE = 60*15000;
    private static final long UNITS_PER_SECOND = 15000;

    private final long units;

    public HourCoordinate(int hours, int minutes, double seconds) {
        int sign = hours == 0 ? 1 :hours / Math.abs(hours);
        long unitsNoModNoSign = sign*UNITS_PER_HOUR*hours
                + UNITS_PER_MINUTE*minutes
                + (int)(UNITS_PER_SECOND*seconds);
        long unitsBeforePositiveNormalization = (sign*unitsNoModNoSign) % MAX_UNITS;
        this.units = (unitsBeforePositiveNormalization + MAX_UNITS) % MAX_UNITS;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HourCoordinate other) {
            return this.units == other.units;
        }
            return false;
        }
    }