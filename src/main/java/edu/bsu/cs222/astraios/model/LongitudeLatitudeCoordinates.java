package edu.bsu.cs222.astraios.model;

public class LongitudeLatitudeCoordinates {

    private final FullCircleDegreeCoordinate longitude;
    private final HalfCircleDegreeCoordinate latitude;

    public LongitudeLatitudeCoordinates(FullCircleDegreeCoordinate longitude, HalfCircleDegreeCoordinate latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof LongitudeLatitudeCoordinates other) {
            return this.latitude.equals(other.latitude) && this.longitude.equals(other.longitude);
        }
        return false;
    }

    public FullCircleDegreeCoordinate getLongitude() {
        return longitude;
    }

    public HalfCircleDegreeCoordinate getLatitude() {
        return latitude;
    }
}
