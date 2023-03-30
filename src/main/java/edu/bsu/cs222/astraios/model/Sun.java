package edu.bsu.cs222.astraios.model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Sun {
    public AltitudeAzimuthCoordinates getLocationDuringObservation(Observation zeroZeroEpoch) {
        return new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(178,3,34.38),
                new HalfCircleDegreeCoordinate(66,57,37.3356)
        );
    }

    private double getEquationOfTime(double fractionalYear) {
        //Constants are from the NOAA low accuracy calculation in radians
        //Preserved here for verification
        return 229.18*(
                0.000075
                + 0.001868*cos(fractionalYear)
                - 0.032077*sin(fractionalYear)
                - 0.014615*cos(2*fractionalYear)
                - 0.040849*sin(2*fractionalYear)
        );
    }

    private double getSolarDeclination(double fractionalYear) {
        //Constants are from the NOAA low accuracy calculation in radians
        //Preserved here for verification
        return 0.06918
                - 0.399912*cos(fractionalYear)
                + 0.070257*sin(fractionalYear)
                - 0.006758*cos(2*fractionalYear)
                + 0.000907*sin(2*fractionalYear)
                - 0.002697*cos(3*fractionalYear)
                + 0.001480*sin(3*fractionalYear);
    }
}
