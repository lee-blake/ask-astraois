package edu.bsu.cs222.astraios.model;

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
}
