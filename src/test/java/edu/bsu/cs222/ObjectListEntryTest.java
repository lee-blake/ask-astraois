package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectListEntryTest {

    public AstronomicalObject buildM13Object() {
        HourCoordinate m13RA = new HourCoordinate(16,41,41.24);
        HalfCircleDegreeCoordinate m13Dec = new HalfCircleDegreeCoordinate(36,27,35.5);
        RightAscensionDeclinationCoordinates m13Coords = new RightAscensionDeclinationCoordinates(m13RA,m13Dec);
        return new AstronomicalObject("M13",m13Coords);
    }

    public AstronomicalObject buildM31Object() {
        HourCoordinate m31RA = new HourCoordinate(0,42,44.30);
        HalfCircleDegreeCoordinate m31Dec = new HalfCircleDegreeCoordinate(41,16,9);
        RightAscensionDeclinationCoordinates m31Coords = new RightAscensionDeclinationCoordinates(m31RA,m31Dec);
        return new AstronomicalObject("M31",m31Coords);
    }

    @Test
    public void testGetNameM13() {
        AstronomicalObject m13Object = buildM13Object();
        ObjectListEntry entry = new ObjectListEntry(m13Object);
        String nameRetrieved = entry.getName();
        Assertions.assertEquals("M13",nameRetrieved);
    }

    @Test
    public void testGetNameM31() {
        AstronomicalObject m31Object = buildM31Object();
        ObjectListEntry entry = new ObjectListEntry(m31Object);
        String nameRetrieved = entry.getName();
        Assertions.assertEquals("M31",nameRetrieved);
    }

    @Test
    public void testGetObjectM13() {
        AstronomicalObject m13Object = buildM13Object();
        ObjectListEntry entry = new ObjectListEntry(m13Object);
        AstronomicalObject freshM13Object = buildM13Object();
        AstronomicalObject objectRetrieved = entry.getAstronomicalObject();
        boolean result = freshM13Object.equals(objectRetrieved);
        Assertions.assertTrue(result);
    }

    @Test
    public void testGetObjectM31() {
        AstronomicalObject m31Object = buildM31Object();
        ObjectListEntry entry = new ObjectListEntry(m31Object);
        AstronomicalObject freshM31Object = buildM31Object();
        AstronomicalObject retrievedObject = entry.getAstronomicalObject();
        boolean result = freshM31Object.equals(retrievedObject);
        Assertions.assertTrue(result);
    }
}
