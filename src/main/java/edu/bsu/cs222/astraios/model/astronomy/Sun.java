package edu.bsu.cs222.astraios.model.astronomy;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.acos;

public class Sun {

    private static final double NIGHT_UPPER_CUTOFF_RADIANS = -Math.PI*18/180;
    private static final double TWILIGHT_UPPER_CUTOFF_RADIANS = -Math.PI*12/180;

    public AzimuthAltitudeCoordinates getLocationDuringObservation(Observation observation) {
        double fractionalYear = observation.getFractionalYear();
        double equationOfTime = getEquationOfTime(fractionalYear);
        double hourAngle = observation.getSolarHourAngleInRadians(equationOfTime);
        double declination = getSolarDeclination(fractionalYear);
        double latitude = observation.getLatitudeAsRadians();

        double cosOfZenith = sin(latitude)*sin(declination)
                + cos(latitude)*cos(declination)*cos(hourAngle);
        double zenithAngle = acos(cosOfZenith);
        double cosOfAzimuthComplement = (sin(latitude)*cos(zenithAngle) - sin(declination))
                / (cos(latitude)*sin(zenithAngle));
        double azimuth = Math.PI - acos(cosOfAzimuthComplement);
        if(sin(hourAngle) > 0) {
            // Remember that acos only returns values in a 180 degree interval. Therefore, the negative angles will be
            // interpreted as their absolute values once acos compose cos is applied to them. Correcting this entails
            // returning to a negative value when appropriate, and for the calculation we use, that is when the hour
            // angle is in (0, pi).
            azimuth *= -1;
        }
        double altitude = Math.PI/2-zenithAngle;
        return new AzimuthAltitudeCoordinates(
                FullCircleDegreeCoordinate.fromRadians(azimuth),
                HalfCircleDegreeCoordinate.fromRadians(altitude)
        );
    }

    private double getEquationOfTime(double fractionalYear) {
        //Constants are from the NOAA low accuracy calculation in minutes
        //Preserved here for verification
        double equationOfTimeInMinutes = 229.18*(
                0.000075
                + 0.001868*cos(fractionalYear)
                - 0.032077*sin(fractionalYear)
                - 0.014615*cos(2*fractionalYear)
                - 0.040849*sin(2*fractionalYear)
        );
        return equationOfTimeInMinutes*Math.PI/720;
    }

    private double getSolarDeclination(double fractionalYear) {
        //Constants are from the NOAA low accuracy calculation in radians
        //Preserved here for verification
        return 0.006918
                - 0.399912*cos(fractionalYear)
                + 0.070257*sin(fractionalYear)
                - 0.006758*cos(2*fractionalYear)
                + 0.000907*sin(2*fractionalYear)
                - 0.002697*cos(3*fractionalYear)
                + 0.001480*sin(3*fractionalYear);
    }

    public boolean sunInterferesWithViewing(Observation observation) {
        return !(this.isNight(observation) || this.isAstronomicalTwilight(observation));
    }

    public boolean isNight(Observation observation) {
        return this.getLocationDuringObservation(observation).getAltitudeInRadians() < NIGHT_UPPER_CUTOFF_RADIANS;
    }

    public boolean isAstronomicalTwilight(Observation observation) {
        double altitudeRadians = this.getLocationDuringObservation(observation).getAltitudeInRadians();
        return altitudeRadians >= NIGHT_UPPER_CUTOFF_RADIANS && altitudeRadians < TWILIGHT_UPPER_CUTOFF_RADIANS;
    }
}
