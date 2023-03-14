package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static edu.bsu.cs222.astraios.model.TestObjectFactory.AstronomicalObjects.buildM13Object;
import static edu.bsu.cs222.astraios.model.TestObjectFactory.AstronomicalObjects.buildM31Object;

public class ObjectListEntryTest {

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


    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonObjectListEntryNotEqual() {
        ObjectListEntry entry = new ObjectListEntry(buildM13Object());
        String otherObject = "";
        boolean result = entry.equals(otherObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameObjectAndCompletionIsEqual() {
        ObjectListEntry entry1 = new ObjectListEntry(buildM13Object());
        ObjectListEntry entry2 = new ObjectListEntry(buildM13Object());
        boolean result = entry1.equals(entry2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentObjectNotEqual() {
        ObjectListEntry entry1 = new ObjectListEntry(buildM13Object());
        ObjectListEntry entry2 = new ObjectListEntry(buildM31Object());
        boolean result = entry1.equals(entry2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentCompletionNotEqual() {
        ObjectListEntry incomplete = new ObjectListEntry(buildM13Object());
        ObjectListEntry complete = new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        boolean result = incomplete.equals(complete);
        Assertions.assertFalse(result);
    }



    @Test
    public void testMarkCompleteFirstOf2023() throws EntryAlreadyCompleteException {
        ObjectListEntry expected = new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        ObjectListEntry actual = new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus()
        );
        actual.markComplete(LocalDate.parse("2023-01-01"));
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testMarkCompleteLastOf2022() throws EntryAlreadyCompleteException {
        ObjectListEntry expected = new ObjectListEntry(
                buildM31Object(),
                new CompletionStatus(LocalDate.parse("2022-12-31"))
        );
        ObjectListEntry actual = new ObjectListEntry(
                buildM31Object(),
                new CompletionStatus()
        );
        actual.markComplete(LocalDate.parse("2022-12-31"));
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testMarkCompleteThrowsWhenAlreadyComplete() {
        ObjectListEntry entry = new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        Assertions.assertThrows(
            EntryAlreadyCompleteException.class,
                () -> entry.markComplete(LocalDate.parse("2022-12-31"))
        );
    }



    @Test
    public void testMarkIncompleteMarksIncomplete() throws EntryAlreadyIncompleteException {
        ObjectListEntry expected = new ObjectListEntry(
            buildM13Object(),
            new CompletionStatus()
        );
        ObjectListEntry actual = new ObjectListEntry(
            buildM13Object(),
            new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        actual.markIncomplete();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testMarkIncompleteThrowsExceptionIfAlreadyIncomplete() {
        ObjectListEntry entry = new ObjectListEntry(
                buildM13Object(),
                new CompletionStatus()
        );
        Assertions.assertThrows(
            EntryAlreadyIncompleteException.class,
                entry::markIncomplete
        );
    }
}
