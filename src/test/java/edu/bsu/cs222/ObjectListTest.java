package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.NameNotFoundException;
import java.time.LocalDate;

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
    public void testAddEntry_GetObjectByName() throws ObjectListEntryAlreadyExistsException, NameNotFoundException {
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
    public void testGetObjectByNameObjectThrowsExceptionWhenNameMissing() throws ObjectListEntryAlreadyExistsException {
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectListEntry m31Entry = new ObjectListEntry(buildM31Object());
        ObjectList objectList = new ObjectList();
        objectList.addEntry(m13Entry);
        objectList.addEntry(m31Entry);
        Assertions.assertThrows(
                NameNotFoundException.class, () -> objectList.getEntryByName("m14"));
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


    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonObjectListNotEqual() {
        ObjectList list = new ObjectList();
        String otherObject = "";
        boolean result = list.equals(otherObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsBothEmptyIsEqual() {
        ObjectList list1 = new ObjectList();
        ObjectList list2 = new ObjectList();
        boolean result = list1.equals(list2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentOneElementNotEqual() throws ObjectListEntryAlreadyExistsException {
        ObjectList list1 = new ObjectList();
        list1.addEntry(new ObjectListEntry(buildM13Object()));
        ObjectList list2 = new ObjectList();
        list2.addEntry(new ObjectListEntry(buildM31Object()));
        boolean result = list1.equals(list2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsIdenticalNonemptyIsEqual() throws ObjectListEntryAlreadyExistsException {
        ObjectList list1 = new ObjectList();
        list1.addEntry(new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        list1.addEntry(new ObjectListEntry(buildM31Object()));
        ObjectList list2 = new ObjectList();
        list2.addEntry(new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        list2.addEntry(new ObjectListEntry(buildM31Object()));
        boolean result = list1.equals(list2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsProperSubsetNotEqual() throws ObjectListEntryAlreadyExistsException {
        ObjectList list1 = new ObjectList();
        list1.addEntry(new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        ObjectList list2 = new ObjectList();
        list2.addEntry(new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        list2.addEntry(new ObjectListEntry(buildM31Object()));
        boolean result = list1.equals(list2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsRemoveEntry() throws NameNotFoundException, ObjectListEntryAlreadyExistsException {
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectListEntry m31Entry = new ObjectListEntry(buildM31Object());
        ObjectList objectList1 = new ObjectList();
        objectList1.addEntry(m13Entry);
        objectList1.addEntry(m31Entry);

        ObjectListEntry freshm13Entry = new ObjectListEntry(buildM13Object());
        ObjectList objectList2 = new ObjectList();
        objectList2.addEntry(freshm13Entry);

        objectList1.removeEntry(m31Entry);
        boolean result = objectList1.equals(objectList2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testRemoveEntryThrowsNameNotFoundException() throws ObjectListEntryAlreadyExistsException {
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectListEntry m31Entry = new ObjectListEntry(buildM31Object());
        ObjectList objectList = new ObjectList();
        objectList.addEntry(m13Entry);

        Assertions.assertThrows(
                NameNotFoundException.class, () -> objectList.removeEntry(m31Entry));

    }
}