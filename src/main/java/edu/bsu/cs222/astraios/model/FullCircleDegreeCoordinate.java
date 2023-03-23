package edu.bsu.cs222.astraios.model;


public class FullCircleDegreeCoordinate {

    private static final long MAX_UNITS = 360*60*60*1000;
    private static final long UNITS_PER_DEGREE = 60*60*1000;
    private static final long UNITS_PER_ARCMINUTE = 60*1000;
    private static final long UNITS_PER_ARCSECOND = 1000;

    private final long units;



    public boolean equals(Object o) {
        if(o instanceof FullCircleDegreeCoordinate other) {
            return this.units == other.units;
        }
        return false;
    }

    public FullCircleDegreeCoordinate(int degrees, int arcminutes, double arcseconds) {


        // The sign for the entire expression is given on the degrees variable. To make sure arcminutes and arcseconds
        // also calculate in the same direction, we momentarily drop the sign on degrees while adding them all up.
        int sign = (degrees == 0) ? 1 : degrees / Math.abs(degrees);
        long unitsNoModNoSign = sign*UNITS_PER_DEGREE*degrees
                + UNITS_PER_ARCMINUTE *arcminutes
                + (long)(UNITS_PER_ARCSECOND*arcseconds);
        long unitsBeforePositiveNormalization = (sign*unitsNoModNoSign) % MAX_UNITS;
        this.units = (unitsBeforePositiveNormalization + MAX_UNITS) % MAX_UNITS;
    }
}
