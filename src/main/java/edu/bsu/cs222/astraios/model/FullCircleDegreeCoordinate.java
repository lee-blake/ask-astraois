package edu.bsu.cs222.astraios.model;


import java.text.DecimalFormat;

public class FullCircleDegreeCoordinate {

    private static final long MAX_UNITS = 360*60*60*1000;
    private static final long UNITS_PER_DEGREE = 60*60*1000;
    private static final long UNITS_PER_ARCMINUTE = 60*1000;
    private static final long UNITS_PER_ARCSECOND = 1000;
    private static final long UNITS_FOR_PI_RADIANS = 180*60*60*1000;

    private final long units;

    public FullCircleDegreeCoordinate(int degrees, int arcminutes, double arcseconds) {
        if(!coordinatesAreValid(degrees, arcminutes, arcseconds)) {
            throw new IllegalArgumentException(
                    "Degree coordinates '"
                            + degrees + ","
                            + arcminutes + ","
                            + arcseconds + "'"
                            + "were not valid full-circle coordinates! Arcminutes and arcseconds must be in [0,60) "
            );
        }

        // The sign for the entire expression is given on the degrees variable. To make sure arcminutes and arcseconds
        // also calculate in the same direction, we momentarily drop the sign on degrees while adding them all up.
        int sign = (degrees == 0) ? 1 : degrees / Math.abs(degrees);
        long unitsNoModNoSign = sign*UNITS_PER_DEGREE*degrees
                + UNITS_PER_ARCMINUTE *arcminutes
                + Math.round(UNITS_PER_ARCSECOND*arcseconds);
        long unitsBeforePositiveNormalization = (sign*unitsNoModNoSign) % MAX_UNITS;
        this.units = (unitsBeforePositiveNormalization + MAX_UNITS) % MAX_UNITS;
    }

    public static FullCircleDegreeCoordinate fromRadians(double radians) {
        return new FullCircleDegreeCoordinate(radians);
    }

    private FullCircleDegreeCoordinate(double radians) {
        this.units = ((Math.round(radians*UNITS_FOR_PI_RADIANS / Math.PI) % MAX_UNITS) + MAX_UNITS) % MAX_UNITS;
    }

    private boolean coordinatesAreValid(int ignoredDegrees, int arcminutes, double arcseconds) {
        return arcminutes <= 59 && arcminutes >= 0
                && !(arcseconds >= 60) && !(arcseconds < 0);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof FullCircleDegreeCoordinate other) {
            return this.units == other.units;
        }
        return false;
    }

    @Override
    public String toString() {
        FullCircleDegreeCoordinate.FullCircleDegreeCoordinateFormatter formatter =
                this.new FullCircleDegreeCoordinateFormatter();
        return formatter.standardDegreeFormatNoSpaces();
    }

    public double toRadians() {return Math.PI*this.units/UNITS_FOR_PI_RADIANS;}

    public FullCircleDegreeCoordinate negate() {
        return FullCircleDegreeCoordinate.fromRadians(-this.toRadians());
    }


    public class FullCircleDegreeCoordinateFormatter {

        private final long degrees;
        private final long arcminutes;
        private final double arcseconds;

        public FullCircleDegreeCoordinateFormatter() {
            this.degrees = units/UNITS_PER_DEGREE;
            this.arcminutes = (units % UNITS_PER_DEGREE)/UNITS_PER_ARCMINUTE;
            this.arcseconds = (units % UNITS_PER_ARCMINUTE)/(double)UNITS_PER_ARCSECOND;
        }

        public String standardDegreeFormatNoSpaces() {
            return String.format("%02d°%02d'",this.degrees,this.arcminutes)
                    + new DecimalFormat("00.#########").format(this.arcseconds) + "\"";
        }

        public String standardDegreeFormatNoSpacesOneDecimalPlace() {
            return String.format("%02d°%02d'",this.degrees,this.arcminutes)
                    + new DecimalFormat("00.#").format(this.arcseconds) + "\"";
        }
    }
}
