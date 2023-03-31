package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AltitudeAzimuthCoordinatesTest {

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsDifferentTypeNotEqual() {
        HalfCircleDegreeCoordinate altitude = new HalfCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate azimuth = new FullCircleDegreeCoordinate(90,0,0);
        AltitudeAzimuthCoordinates coordinates = new AltitudeAzimuthCoordinates(azimuth, altitude);
        String otherTypeObject = "";
        boolean result = coordinates.equals(otherTypeObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsBothSameEqual() {
        HalfCircleDegreeCoordinate altitude0 = new HalfCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate azimuth0 = new FullCircleDegreeCoordinate(90,0,0);
        AltitudeAzimuthCoordinates coordinates0 = new AltitudeAzimuthCoordinates(azimuth0, altitude0);
        HalfCircleDegreeCoordinate altitude1 = new HalfCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate azimuth1 = new FullCircleDegreeCoordinate(90,0,0);
        AltitudeAzimuthCoordinates coordinates1 = new AltitudeAzimuthCoordinates(azimuth1, altitude1);
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsAltitudeCoordinateDifferenceNotEqual() {
        HalfCircleDegreeCoordinate altitude0 = new HalfCircleDegreeCoordinate(45,10,10);
        FullCircleDegreeCoordinate azimuth0 = new FullCircleDegreeCoordinate(90,0,0);
        AltitudeAzimuthCoordinates coordinates0 = new AltitudeAzimuthCoordinates(azimuth0, altitude0);
        HalfCircleDegreeCoordinate altitude1 = new HalfCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate azimuth1 = new FullCircleDegreeCoordinate(90,0,0);
        AltitudeAzimuthCoordinates coordinates1 = new AltitudeAzimuthCoordinates(azimuth1, altitude1);
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsAzimuthCoordinateDifferenceNotEqual() {
        HalfCircleDegreeCoordinate altitude0 = new HalfCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate azimuth0 = new FullCircleDegreeCoordinate(45,0,0);
        AltitudeAzimuthCoordinates coordinates0 = new AltitudeAzimuthCoordinates(azimuth0, altitude0);
        HalfCircleDegreeCoordinate altitude1 = new HalfCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate azimuth1 = new FullCircleDegreeCoordinate(90,0,0);
        AltitudeAzimuthCoordinates coordinates1 = new AltitudeAzimuthCoordinates(azimuth1, altitude1);
        boolean result = coordinates0.equals(coordinates1);
        Assertions.assertFalse(result);
    }



    @Test
    public void testDistanceToSameCoordinatesZero() {
        HalfCircleDegreeCoordinate altitude0 = new HalfCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate azimuth0 = new FullCircleDegreeCoordinate(45,0,0);
        AltitudeAzimuthCoordinates coordinates0 = new AltitudeAzimuthCoordinates(azimuth0, altitude0);
        HalfCircleDegreeCoordinate altitude1 = new HalfCircleDegreeCoordinate(0,0,0);
        FullCircleDegreeCoordinate azimuth1 = new FullCircleDegreeCoordinate(45,0,0);
        AltitudeAzimuthCoordinates coordinates1 = new AltitudeAzimuthCoordinates(azimuth1, altitude1);
        AngularDistance actual = coordinates0.distanceTo(coordinates1);
        AngularDistance expected = new AngularDistance(0,0,0);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testDistanceToPoles180() {
        HalfCircleDegreeCoordinate altitude0 = new HalfCircleDegreeCoordinate(90,0,0);
        FullCircleDegreeCoordinate azimuth0 = new FullCircleDegreeCoordinate(45,0,0);
        AltitudeAzimuthCoordinates coordinates0 = new AltitudeAzimuthCoordinates(azimuth0, altitude0);
        HalfCircleDegreeCoordinate altitude1 = new HalfCircleDegreeCoordinate(-90,0,0);
        FullCircleDegreeCoordinate azimuth1 = new FullCircleDegreeCoordinate(45,0,0);
        AltitudeAzimuthCoordinates coordinates1 = new AltitudeAzimuthCoordinates(azimuth1, altitude1);
        AngularDistance actual = coordinates0.distanceTo(coordinates1);
        AngularDistance expected = new AngularDistance(180,0,0);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testDistanceMixedCoordinates() {
        HalfCircleDegreeCoordinate altitude0 = new HalfCircleDegreeCoordinate(45,0,0);
        FullCircleDegreeCoordinate azimuth0 = new FullCircleDegreeCoordinate(0,0,0);
        AltitudeAzimuthCoordinates coordinates0 = new AltitudeAzimuthCoordinates(azimuth0, altitude0);
        HalfCircleDegreeCoordinate altitude1 = new HalfCircleDegreeCoordinate(-45,0,0);
        FullCircleDegreeCoordinate azimuth1 = new FullCircleDegreeCoordinate(90,0,0);
        AltitudeAzimuthCoordinates coordinates1 = new AltitudeAzimuthCoordinates(azimuth1, altitude1);
        AngularDistance actual = coordinates0.distanceTo(coordinates1);
        AngularDistance expected = new AngularDistance(120,0,0);
        Assertions.assertEquals(expected,actual);
    }



    @Test
    public void testIsAboveHorizonPlusOneThousandthOfOneArcsecondIsAbove() {
        AltitudeAzimuthCoordinates above = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(0,0,0),
                new HalfCircleDegreeCoordinate(0,0,0.001)
        );
        boolean result = above.isAboveHorizon();
        Assertions.assertTrue(result);
    }
    @Test
    public void testIsAboveHorizonMinusOneThousandthOfOneArcsecondIsNotAbove() {
        AltitudeAzimuthCoordinates above = new AltitudeAzimuthCoordinates(
                new FullCircleDegreeCoordinate(0,0,0),
                new HalfCircleDegreeCoordinate(0,0,0.001).negate()
        );
        boolean result = above.isAboveHorizon();
        Assertions.assertFalse(result);
    }
}
