package edu.bsu.cs222.astraios.model;

import java.text.DecimalFormat;

public class HourCoordinate {

    // Units are chosen so that resolution goes down to 1/15 of a millisecond.
    // This is equivalent to two inches of longitude/latitude.
    // This should be adequate for this project.
    private static final long MAX_UNITS = 24*60*60*15000;
    private static final long UNITS_PER_HOUR = 60*60*15000;
    private static final long UNITS_PER_MINUTE = 60*15000;
    private static final long UNITS_PER_SECOND = 15000;
    private static final long UNITS_FOR_PI_RADIANS = 12*60*60*15000;


    private final long units;

    public HourCoordinate(int hours, int minutes, double seconds) {
        if(!coordinatesAreValid(hours, minutes, seconds)) {
            throw new IllegalArgumentException(
                    "hour coordinates '"
                            + hours + ","
                            + minutes + ","
                            + seconds + "'"
                            + "were not valid hour coordinates! Minutes and seconds must be in [0,60)."
            );
        }
        // The sign for the entire expression is given on the hours variable. To make sure minutes and seconds
        // also calculate in the same direction, we momentarily drop the sign on hours while adding them all up.
        int sign = (hours == 0) ? 1 :hours / Math.abs(hours);
        // It's also easiest to add them before taking the modulus.
        long unitsNoModNoSign = sign*UNITS_PER_HOUR*hours
                + UNITS_PER_MINUTE*minutes
                + Math.round(UNITS_PER_SECOND*seconds);
        long unitsBeforePositiveNormalization = (sign*unitsNoModNoSign) % MAX_UNITS;
        // The modulus must be taken again in case the original was negative.
        this.units = (unitsBeforePositiveNormalization + MAX_UNITS) % MAX_UNITS;
    }

    private boolean coordinatesAreValid(int ignoredHours, int minutes, double seconds) {
        return minutes >= 0
                && minutes < 60
                && seconds >= 0
                && seconds < 60;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof HourCoordinate other) {
            return this.units == other.units;
        }
            return false;
    }

    public double toRadians() {
        return Math.PI*this.units/UNITS_FOR_PI_RADIANS;
    }


    public class HourCoordinateFormatter {

        private final long hours;
        private final long minutes;
        private final double seconds;

        public HourCoordinateFormatter() {
            this.hours = units/UNITS_PER_HOUR;
            this.minutes = (units % UNITS_PER_HOUR)/UNITS_PER_MINUTE;
            this.seconds = (units % UNITS_PER_MINUTE)/(double)UNITS_PER_SECOND;
        }

        public String standardHourFormatSpaced() {
            return String.format("%02dh %02dm ",this.hours,this.minutes)
                    + (new DecimalFormat("00.#########")).format(this.seconds) + "s";
        }

        public String standardHourFormatNoSpacesOneDecimalPlace() {
            return String.format("%02dh%02dm",this.hours,this.minutes)
                    + (new DecimalFormat("00.0")).format(this.seconds) + "s";
        }
    }
}
