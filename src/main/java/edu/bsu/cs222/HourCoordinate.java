package edu.bsu.cs222;

import java.text.DecimalFormat;

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
        int sign = (hours == 0) ? 1 :hours / Math.abs(hours);
        // The sign for the entire expression is given on hours. To make sure arcminutes and arcseconds
        // also calculate in the same direction, we momentarily drop the sign on hours while adding them all up.
        // It's also easiest to add them before taking the modulus.
        long unitsNoModNoSign = sign*UNITS_PER_HOUR*hours
                + UNITS_PER_MINUTE*minutes
                + (int)(UNITS_PER_SECOND*seconds);
        long unitsBeforePositiveNormalization = (sign*unitsNoModNoSign) % MAX_UNITS;
        // The modulus must be taken again in case the original was negative.
        this.units = (unitsBeforePositiveNormalization + MAX_UNITS) % MAX_UNITS;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HourCoordinate other) {
            return this.units == other.units;
        }
            return false;
        }



    public class HourCoordinateFormatter {

        public String standardHourFormatSpaced() {
            long hours = units/UNITS_PER_HOUR;
            long minutes = (units % UNITS_PER_HOUR)/UNITS_PER_MINUTE;
            double seconds = (units % UNITS_PER_MINUTE)/(double)UNITS_PER_SECOND;
            return String.format("%02dh %02dm ",hours,minutes)
                    +(new DecimalFormat("00.#########")).format(seconds) + "s";
        }
    }
}
