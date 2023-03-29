package edu.bsu.cs222.astraios.model;

import java.text.DecimalFormat;

public class HalfCircleDegreeCoordinate {

    // Units are chosen so that resolution goes down to 1/1000 of an arcsecond.
    // This is equivalent to two inches of longitude/latitude or 1/15 of a millisecond of Earth's rotation.
    // This should be adequate for this project.
    private static final long UNITS_PER_DEGREE = 60*60*1000;
    private static final long UNITS_PER_ARCMINUTE = 60*1000;
    private static final long UNITS_PER_ARCSECOND = 1000;
    private static final long UNITS_FOR_PI_RADIANS = 180*60*60*1000;

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
        // The sign for the entire expression is given on the degrees variable. To make sure arcminutes and arcseconds
        // also calculate in the same direction, we momentarily drop the sign on degrees while adding them all up.
        int sign = (degrees == 0) ? 1 : degrees / Math.abs(degrees);
        long unitUnsigned = sign*UNITS_PER_DEGREE*degrees
                + UNITS_PER_ARCMINUTE *arcminutes
                + Math.round(UNITS_PER_ARCSECOND*arcseconds);
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

    public static HalfCircleDegreeCoordinate fromRadians(double radians) {
        if(!radiansCoordinatesAreValid(radians)) {
            throw new IllegalArgumentException(
                    "Radians coordinates '"
                            + radians
                            + "' are not valid half circle coordinates because half circle coordinates must "
                            + "be in [-π,π]!"
            );
        }
        return new HalfCircleDegreeCoordinate(radians);
    }

    private static boolean radiansCoordinatesAreValid(double radians) {
        return radians <= Math.PI/2 && radians >= -Math.PI/2;
    }

    private HalfCircleDegreeCoordinate(double radians) {
        this.units = Math.round(UNITS_FOR_PI_RADIANS*radians/Math.PI);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof HalfCircleDegreeCoordinate other) {
            return this.units == other.units;
        }
        return false;
    }

    @Override
    public String toString() {
        HalfCircleDegreeCoordinate.HalfCircleDegreeCoordinateFormatter formatter =
                this.new HalfCircleDegreeCoordinateFormatter();
        return formatter.standardDegreeFormatNoSpaces();
    }

    public double toRadians() {
        return Math.PI*this.units/UNITS_FOR_PI_RADIANS;
    }

    public HalfCircleDegreeCoordinate negate() {
        return HalfCircleDegreeCoordinate.fromRadians(-this.toRadians());
    }


    public class HalfCircleDegreeCoordinateFormatter {

        private final long degrees;
        private final long arcminutes;
        private final double arcseconds;

        public HalfCircleDegreeCoordinateFormatter() {
            this.degrees = units/UNITS_PER_DEGREE;
            this.arcminutes = Math.abs((units % UNITS_PER_DEGREE)/UNITS_PER_ARCMINUTE);
            this.arcseconds = Math.abs((units % UNITS_PER_ARCMINUTE)/(double)UNITS_PER_ARCSECOND);
        }

        public String standardDegreeFormatSpaced() {
            return String.format("%02d° %02d' ",this.degrees,this.arcminutes)
                    +(new DecimalFormat("00.#########")).format(this.arcseconds) + "\"";
        }

        public String standardDegreeFormatNoSpaces() {
            return String.format("%02d°%02d'",this.degrees,this.arcminutes)
                    +(new DecimalFormat("00.#########")).format(this.arcseconds) + "\"";
        }

        public String standardDegreeFormatNoSpacesOneDecimalPlace() {
            return String.format("%02d°%02d'",this.degrees,this.arcminutes)
                    +(new DecimalFormat("00.0")).format(this.arcseconds) + "\"";
        }
    }
}
