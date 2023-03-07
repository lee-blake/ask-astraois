package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectListEntryTest {

    @Test
    public void testGetNameM13() {
        HourCoordinate m13RA = new HourCoordinate(16,41,41.24);
        HalfCircleDegreeCoordinate m13Dec = new HalfCircleDegreeCoordinate(36,27,35.5);
        RightAscensionDeclinationCoordinates m13Coords = new RightAscensionDeclinationCoordinates(m13RA,m13Dec);
        AstronomicalObject m13Object = new AstronomicalObject("M13",m13Coords);
        ObjectListEntry entry = new ObjectListEntry(m13Object);
        String nameRetrieved = entry.getName();
        Assertions.assertEquals("M13",nameRetrieved);
    }

    @Test
    public void testGetNameM31() {
        HourCoordinate m31RA = new HourCoordinate(0,42,44.30);
        HalfCircleDegreeCoordinate m31Dec = new HalfCircleDegreeCoordinate(41,16,9);
        RightAscensionDeclinationCoordinates m31Coords = new RightAscensionDeclinationCoordinates(m31RA,m31Dec);
        AstronomicalObject m31Object = new AstronomicalObject("M31",m31Coords);
        ObjectListEntry entry = new ObjectListEntry(m31Object);
        String nameRetrieved = entry.getName();
        Assertions.assertEquals("M31",nameRetrieved);
    }
}
