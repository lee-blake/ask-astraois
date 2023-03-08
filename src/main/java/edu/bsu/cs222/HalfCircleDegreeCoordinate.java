package edu.bsu.cs222;

public class HalfCircleDegreeCoordinate {

    // Resolution goes down to 1/1000 of an arcsecond
    // (two inches long/lat or 1/15 of a millisecond of Earth's rotation).
    // This should be adequate for this project.
    private static final long UNITS_PER_DEGREE = 60*60*1000;
    private static final long UNITS_PER_ARCMINUTE = 60*1000;
    private static final long UNITS_PER_ARCSECOND = 1000;

    private final long units;

    public HalfCircleDegreeCoordinate(int degrees, int arcminutes, double arcseconds) {
        if(!coordinatesAreValid(degrees, arcminutes, arcseconds)) {
            throw new IllegalArgumentException(
                    "Degree coordinates '"
                            + degrees + ","
                            + arcminutes + ","
                            + arcseconds + "'"
                            + "were not valid half-circle coordinates! Arcminutes and arcseconds must be in [0,60) "
                            + " and degrees in (-90,90) except for coordinates <90,0,0> and <-90,0,0>."
            );
        }
        int sign = degrees == 0 ? 1 : degrees / Math.abs(degrees);
        long unitsNoModNoSign = sign*UNITS_PER_DEGREE*degrees
                + UNITS_PER_ARCMINUTE *arcminutes
                + (long)(UNITS_PER_ARCSECOND*arcseconds);
        this.units = sign*unitsNoModNoSign;
    }

    private boolean coordinatesAreValid(int degrees, int arcminutes, double arcseconds) {
        if(
                degrees > 90 || degrees < -90
                || arcminutes > 59 || arcminutes < 0
                || arcseconds >= 60 || arcseconds < 0
        ) {
            return false;
        }
        else if(degrees == 90 || degrees == -90) {
            return arcminutes == 0 && arcseconds == 0;
        }
        else {
            return true;
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
