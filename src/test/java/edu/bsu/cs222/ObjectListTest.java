package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectListTest {

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
    public void testAddEntry_GetObjectByName() throws ObjectListEntryAlreadyExistsException {
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectListEntry m31Entry = new ObjectListEntry(buildM31Object());
        ObjectList objectList = new ObjectList();
        objectList.addEntry(m13Entry);
        objectList.addEntry(m31Entry);

        AstronomicalObject freshM13Object = buildM13Object();
        AstronomicalObject firstRetrieved = objectList.getEntryByName("M13").getAstronomicalObject();
        boolean firstResult = freshM13Object.equals(firstRetrieved);
        Assertions.assertTrue(firstResult);

        AstronomicalObject freshM31Object = buildM31Object();
        AstronomicalObject secondRetrieved = objectList.getEntryByName("M31").getAstronomicalObject();
        boolean secondResult = freshM31Object.equals(secondRetrieved);
        Assertions.assertTrue(secondResult);
    }

    @Test
    public void testAddEntryThrowsExceptionOnDuplicateKey() throws ObjectListEntryAlreadyExistsException {
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectList objectList = new ObjectList();
        objectList.addEntry(m13Entry);

        ObjectListEntry duplicateEntry = new ObjectListEntry(buildM13Object());
        Assertions.assertThrows(
                ObjectListEntryAlreadyExistsException.class,
                () -> objectList.addEntry(duplicateEntry)
        );
    }
}
