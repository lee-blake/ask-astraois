package edu.bsu.cs222.astraios.model;

import java.text.DecimalFormat;

public class AngularDistance {

    // Units are chosen so that resolution goes down to 1/1000 of an arcsecond.
    // This is equivalent to two inches of longitude/latitude or 1/15 of a millisecond of Earth's rotation.
    // This should be adequate for this project.
    private static final long UNITS_PER_DEGREE = 60*60*1000;
    private static final long UNITS_PER_ARCMINUTE = 60*1000;
    private static final long UNITS_PER_ARCSECOND = 1000;
    private static final long UNITS_FOR_PI_RADIANS = 180*60*60*1000;

    private final long units;

    public AngularDistance(int degrees, int arcminutes, double arcseconds) {
        if(!coordinatesAreValid(degrees, arcminutes, arcseconds)) {
            throw new IllegalArgumentException("An angular distance must be between 0 and 180 degrees with minutes "
                    + "and seconds in [0,60)!"
            );
        }
        this.units = (long) (degrees*UNITS_PER_DEGREE
                        + arcminutes*UNITS_PER_ARCMINUTE
                        + arcseconds*UNITS_PER_ARCSECOND);
    }

    private boolean coordinatesAreValid(int degrees, int arcminutes, double arcseconds) {
        if(degrees == 180) {
            return arcminutes == 0 && arcseconds == 0;
        }
        return degrees >= 0 && degrees <= 180
                && arcminutes >= 0 && arcminutes < 60
                && arcseconds >= 0 && arcseconds < 60;
    }

    public static AngularDistance fromRadians(double radians) {
        return new AngularDistance(radians);
    }

    private AngularDistance(double radians) {
        if(!coordinatesAreValid(radians)) {
            throw new IllegalArgumentException("An angular distance from radians must be in [0,pi]!");
        }
        this.units = Math.round(radians*UNITS_FOR_PI_RADIANS/Math.PI);
    }

    private boolean coordinatesAreValid(double radians) {
        return radians >= 0 && radians <= Math.PI;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AngularDistance other) {
            return this.units == other.units;
        }
        return false;
    }

    public boolean lessThan(AngularDistance other) {
        return this.units < other.units;
    }

    @Override
    public String toString() {
        long degrees = units  / UNITS_PER_DEGREE;
        long arcminutes = (units % UNITS_PER_DEGREE) / UNITS_PER_ARCMINUTE;
        double arcseconds = (units % UNITS_PER_ARCMINUTE) / (double) UNITS_PER_ARCSECOND;
        String degreesToken = "Δ{" + degrees + "°";
        String arcminutesToken = "" + arcminutes +"'";
        String arcsecondsToken = (new DecimalFormat("#0.#########")).format(arcseconds) + "\"";
        if(arcseconds > 0) {
            return degreesToken + arcminutesToken + arcsecondsToken + "}";
        }
        else if(arcminutes > 0) {
            return degreesToken + arcminutesToken + "}";
        }
        else {
            return degreesToken + "}";
        }
    }
}
