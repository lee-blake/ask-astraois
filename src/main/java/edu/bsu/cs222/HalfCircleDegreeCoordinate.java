package edu.bsu.cs222;

import java.text.DecimalFormat;

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
        // The sign for the entire expression is given on degrees. To make sure arcminutes and arcseconds
        // also calculate in the same direction, we momentarily drop the sign on degrees while adding them all up.
        int sign = (degrees == 0) ? 1 : degrees / Math.abs(degrees);
        long unitUnsigned = sign*UNITS_PER_DEGREE*degrees
                + UNITS_PER_ARCMINUTE *arcminutes
                + (long)(UNITS_PER_ARCSECOND*arcseconds);
        this.units = sign*unitUnsigned;
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



    public class HalfCircleDegreeCoordinateFormatter {

        // Suppressing warnings for the Unicode escaping of the degree character in this file because the fix -
        // replacing the escape with the literal character - has caused unmappable character errors when running this
        // project with default settings. As the inlining of the characters does not impede the functionality, only the
        // readability, and as the context indicates it is the degree character, fixing this warning will be delayed
        // until all team members can meet and confirm that the change does not cause errors on their
        // respective systems.
        @SuppressWarnings("UnnecessaryUnicodeEscape")
        public String standardDegreeFormatSpaced() {
            long degrees = units/UNITS_PER_DEGREE;
            long arcminutes = (units % UNITS_PER_DEGREE)/UNITS_PER_ARCMINUTE;
            double arcseconds = (units % UNITS_PER_ARCMINUTE)/(double)UNITS_PER_ARCSECOND;
            return String.format("%02d\u00b0 %02d' ",degrees,arcminutes)
                    +(new DecimalFormat("00.#########")).format(arcseconds) + "\"";
        }
    }
}
