package edu.bsu.cs222;

public class HalfCircleDegreeCoordinate {

    // Resolution goes down to 1/1000 of an arcsecond
    // (two inches long/lat or 1/15 of a millisecond of Earth's rotation).
    // This should be adequate for this project.
    private static final long MAX_UNITS = 180*60*60*1000;
    private static final long UNITS_PER_DEGREE = 60*60*1000;
    private static final long UNITS_PER_ARCMINUTE = 60*1000;
    private static final long UNITS_PER_ARCSECOND = 1000;

    private final long units;

    public HalfCircleDegreeCoordinate(int degrees, int arcminutes, double arcseconds) {
        int sign = degrees == 0 ? 1 : degrees / Math.abs(degrees);
        long unitsNoModNoSign = sign*UNITS_PER_DEGREE*degrees
                + UNITS_PER_ARCMINUTE *arcminutes
                + (long)(UNITS_PER_ARCSECOND*arcseconds);
        long unitsBeforePositiveNormalization = (sign*unitsNoModNoSign) % MAX_UNITS;
        this.units = (unitsBeforePositiveNormalization + MAX_UNITS) % MAX_UNITS;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HalfCircleDegreeCoordinate other) {
            return this.units == other.units;
        }
        return false;
    }
}
