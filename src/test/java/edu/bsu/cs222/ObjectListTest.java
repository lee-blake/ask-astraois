package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.NameNotFoundException;
import java.time.LocalDate;

import static edu.bsu.cs222.TestObjectFactory.AstronomicalObjects.buildM13Object;
import static edu.bsu.cs222.TestObjectFactory.AstronomicalObjects.buildM31Object;
import static edu.bsu.cs222.TestObjectFactory.ObjectLists.buildM13M31ObjectList;

public class ObjectListTest {

    @Test
    public void testAddEntry_GetObjectByName() throws ObjectListEntryAlreadyExistsException, NameNotFoundException {
        // The list is the same as in TestObjectFactory but should NOT use that list construction
        // because we need to test that AddEntry works before using it in TestObjectFactory methods
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectListEntry m31Entry = new ObjectListEntry(buildM31Object());
        ObjectList objectList = new ObjectList();
        objectList.addEntry(m13Entry);
        objectList.addEntry(m31Entry);

        AstronomicalObject freshM13Object = buildM13Object();
        AstronomicalObject firstRetrieved = objectList.getEntryByName("M13").getAstronomicalObject();
        Assertions.assertEquals(freshM13Object,firstRetrieved);

        AstronomicalObject freshM31Object = buildM31Object();
        AstronomicalObject secondRetrieved = objectList.getEntryByName("M31").getAstronomicalObject();
        Assertions.assertEquals(freshM31Object,secondRetrieved);
    }

    @Test
    public void testGetObjectByNameObjectThrowsExceptionWhenNameMissing() {
        ObjectList objectList = buildM13M31ObjectList();
        Assertions.assertThrows(
                NameNotFoundException.class,
                () -> objectList.getEntryByName("M14")
        );
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
        ObjectList subset = new ObjectList();
        subset.addEntry(new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        ObjectList superset = new ObjectList();
        superset.addEntry(new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        superset.addEntry(new ObjectListEntry(buildM31Object()));
        boolean result = subset.equals(superset);
        Assertions.assertFalse(result);
    }



    @Test
    public void testRemoveEntryActuallyRemoves() throws NameNotFoundException, ObjectListEntryAlreadyExistsException {
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectListEntry m31Entry = new ObjectListEntry(buildM31Object());
        ObjectList actual = new ObjectList();
        actual.addEntry(m13Entry);
        actual.addEntry(m31Entry);

        ObjectListEntry freshM13Entry = new ObjectListEntry(buildM13Object());
        ObjectList expected = new ObjectList();
        expected.addEntry(freshM13Entry);

        actual.removeEntry(m31Entry);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testRemoveEntryThrowsNameNotFoundException() throws ObjectListEntryAlreadyExistsException {
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectListEntry m31Entry = new ObjectListEntry(buildM31Object());
        ObjectList objectList = new ObjectList();
        objectList.addEntry(m13Entry);

        Assertions.assertThrows(
                NameNotFoundException.class,
                () -> objectList.removeEntry(m31Entry)
        );
    }



    @Test
    public void testRemoveEntryByNameActuallyRemoves()
            throws NameNotFoundException, ObjectListEntryAlreadyExistsException {
        ObjectListEntry freshM13Entry = new ObjectListEntry(buildM13Object());
        ObjectList expected = new ObjectList();
        expected.addEntry(freshM13Entry);

        ObjectList actual = buildM13M31ObjectList();
        actual.removeEntryByName("M31");

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testRemoveEntryByNameThrowsNameNotFoundException() throws ObjectListEntryAlreadyExistsException {
        ObjectListEntry m13Entry = new ObjectListEntry(buildM13Object());
        ObjectList objectList = new ObjectList();
        objectList.addEntry(m13Entry);

        Assertions.assertThrows(
                NameNotFoundException.class,
                () -> objectList.removeEntryByName("M31")
        );
    }
}
