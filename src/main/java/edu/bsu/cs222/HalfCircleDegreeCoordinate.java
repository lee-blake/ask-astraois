package edu.bsu.cs222;

public class HalfCircleDegreeCoordinate {

    // Resolution goes down to 1/1000 of an arcsecond
    // (two inches long/lat or 1/15 of a millisecond of Earth's rotation).
    // This should be adequate for this project.
    private static final int MAX_UNITS = 180*60*60*1000;
    private static final int UNITS_PER_DEGREE = 60*60*1000;
    private static final int UNITS_PER_ARCMINUTE = 60*1000;
    private static final int UNITS_PER_ARCSECOND = 1000;

    private final int units;

    public HalfCircleDegreeCoordinate(int degrees, int arcminutes, double arcseconds) {
        int sign = degrees / Math.abs(degrees);
        int unitsNoModNoSign = sign*UNITS_PER_DEGREE*degrees
                + UNITS_PER_ARCMINUTE*arcminutes
                + (int)(UNITS_PER_ARCSECOND*arcseconds);
        int unitsBeforePositiveNormalization = (sign*unitsNoModNoSign) % MAX_UNITS;
        if (unitsBeforePositiveNormalization < 0) {
             this.units = MAX_UNITS + unitsBeforePositiveNormalization;
        }
        else {
            this.units = unitsBeforePositiveNormalization;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HalfCircleDegreeCoordinate other) {
            return this.units == other.units;
        }
        return false;
    }
}
