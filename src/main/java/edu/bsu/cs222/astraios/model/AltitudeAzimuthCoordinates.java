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

    public AngularDistance distanceTo(AltitudeAzimuthCoordinates other) {
        double alt1=this.altitude.toRadians();
        double alt2=other.altitude.toRadians();
        double az1=this.azimuth.toRadians();
        double az2=other.azimuth.toRadians();
        double radianResult=Math.acos(Math.sin(alt1)*Math.sin(alt2)+Math.cos(alt1)*Math.cos(alt2)*Math.cos(az1-az2));
        return AngularDistance.fromRadians(radianResult);
    }
}
