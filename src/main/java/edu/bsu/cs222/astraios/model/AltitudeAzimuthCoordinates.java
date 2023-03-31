package edu.bsu.cs222.astraios.model;

import static java.lang.Math.*;

public class AltitudeAzimuthCoordinates {

    private final FullCircleDegreeCoordinate azimuth;
    private final HalfCircleDegreeCoordinate altitude;

    public AltitudeAzimuthCoordinates(FullCircleDegreeCoordinate azimuth, HalfCircleDegreeCoordinate altitude) {
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AltitudeAzimuthCoordinates other) {
            return this.altitude.equals(other.altitude)  && this.azimuth.equals(other.azimuth);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ALT " + this.altitude + " AZ " + this.azimuth + "]";
    }

    public AngularDistance distanceTo(AltitudeAzimuthCoordinates other) {
        double alt1 = this.altitude.toRadians();
        double alt2 = other.altitude.toRadians();
        double az1 = this.azimuth.toRadians();
        double az2 = other.azimuth.toRadians();
        double angularDistanceRadians = acos(
                sin(alt1)*sin(alt2) + cos(alt1)*cos(alt2)*cos(az1-az2)
        );
        return AngularDistance.fromRadians(angularDistanceRadians);
    }

    public boolean isAboveHorizon() {
        return this.altitude.toRadians() > 0;
    }
}
