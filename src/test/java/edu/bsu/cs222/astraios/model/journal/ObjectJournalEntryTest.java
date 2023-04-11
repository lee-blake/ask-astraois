package edu.bsu.cs222.astraios.model.journal;

import edu.bsu.cs222.astraios.model.astronomy.AstronomicalObject;
import edu.bsu.cs222.astraios.model.exceptions.EntryAlreadyCompleteException;
import edu.bsu.cs222.astraios.model.exceptions.EntryAlreadyIncompleteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static edu.bsu.cs222.astraios.TestObjectFactory.AstronomicalObjects.buildM13Object;
import static edu.bsu.cs222.astraios.TestObjectFactory.AstronomicalObjects.buildM31Object;

public class ObjectJournalEntryTest {

    @Test
    public void testGetNameM13ReturnsM31() {
        AstronomicalObject m13Object = buildM13Object();
        ObjectJournalEntry entry = new ObjectJournalEntry(m13Object);
        String nameRetrieved = entry.getName();
        Assertions.assertEquals("M13", nameRetrieved);
    }

    @Test
    public void testGetNameM31ReturnsM31() {
        AstronomicalObject m31Object = buildM31Object();
        ObjectJournalEntry entry = new ObjectJournalEntry(m31Object);
        String nameRetrieved = entry.getName();
        Assertions.assertEquals("M31", nameRetrieved);
    }



    @Test
    public void testGetAstronomicalObjectM13ReturnsM13() {
        AstronomicalObject m13Object = buildM13Object();
        ObjectJournalEntry entry = new ObjectJournalEntry(m13Object);
        AstronomicalObject freshM13Object = buildM13Object();
        AstronomicalObject objectRetrieved = entry.getAstronomicalObject();
        Assertions.assertEquals(freshM13Object, objectRetrieved);
    }

    @Test
    public void testGetAstronomicalObjectM31ReturnsM31() {
        AstronomicalObject m31Object = buildM31Object();
        ObjectJournalEntry entry = new ObjectJournalEntry(m31Object);
        AstronomicalObject freshM31Object = buildM31Object();
        AstronomicalObject retrievedObject = entry.getAstronomicalObject();
        Assertions.assertEquals(freshM31Object, retrievedObject);
    }



    @Test
    public void testMarkCompleteFirstOf2023MarksCorrectDate() {
        ObjectJournalEntry expected = new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        ObjectJournalEntry actual = new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus()
        );
        actual.markComplete(LocalDate.parse("2023-01-01"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testMarkCompleteLastOf2022MarksCorrectDate() {
        ObjectJournalEntry expected = new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus(LocalDate.parse("2022-12-31"))
        );
        ObjectJournalEntry actual = new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus()
        );
        actual.markComplete(LocalDate.parse("2022-12-31"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testMarkCompleteAlreadyCompleteThrowsException() {
        ObjectJournalEntry entry = new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        Assertions.assertThrows(
            EntryAlreadyCompleteException.class,
                () -> entry.markComplete(LocalDate.parse("2022-12-31"))
        );
    }



    @Test
    public void testMarkIncompleteCompleteEntryMarksIncomplete() {
        ObjectJournalEntry expected = new ObjectJournalEntry(
            buildM13Object(),
            new CompletionStatus()
        );
        ObjectJournalEntry actual = new ObjectJournalEntry(
            buildM13Object(),
            new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        actual.markIncomplete();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testMarkIncompleteAlreadyIncompleteThrowsException() {
        ObjectJournalEntry entry = new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus()
        );
        Assertions.assertThrows(
            EntryAlreadyIncompleteException.class,
                entry::markIncomplete
        );
    }



    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonObjectJournalEntryNotEqual() {
        ObjectJournalEntry entry = new ObjectJournalEntry(buildM13Object());
        String otherObject = "";
        boolean result = entry.equals(otherObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameObjectAndCompletionEqual() {
        ObjectJournalEntry entry1 = new ObjectJournalEntry(buildM13Object());
        ObjectJournalEntry entry2 = new ObjectJournalEntry(buildM13Object());
        boolean result = entry1.equals(entry2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentObjectNotEqual() {
        ObjectJournalEntry entry1 = new ObjectJournalEntry(buildM13Object());
        ObjectJournalEntry entry2 = new ObjectJournalEntry(buildM31Object());
        boolean result = entry1.equals(entry2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentCompletionNotEqual() {
        ObjectJournalEntry incomplete = new ObjectJournalEntry(buildM13Object());
        ObjectJournalEntry complete = new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        boolean result = incomplete.equals(complete);
        Assertions.assertFalse(result);
    }



    @Test
    public void testForceCompleteFirstOf2023MarksCorrectDate() {
        ObjectJournalEntry expected = new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        ObjectJournalEntry actual = new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus()
        );
        actual.forceComplete(LocalDate.parse("2023-01-01"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testForceCompleteLastOf2022MarksCorrectDate() {
        ObjectJournalEntry expected = new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus(LocalDate.parse("2022-12-31"))
        );
        ObjectJournalEntry actual = new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus()
        );
        actual.forceComplete(LocalDate.parse("2022-12-31"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testForceCompleteAlreadyCompleteDoesNotThrow() {
        ObjectJournalEntry entry = new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        );
        entry.forceComplete(LocalDate.parse("2022-12-31"));
    }
}
