package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static edu.bsu.cs222.astraios.model.TestObjectFactory.AstronomicalObjects.buildM13Object;
import static edu.bsu.cs222.astraios.model.TestObjectFactory.AstronomicalObjects.buildM31Object;
import static edu.bsu.cs222.astraios.model.TestObjectFactory.ObjectJournals.buildM13M31ObjectJournal;

public class ObjectJournalTest {

    @Test
    public void testAddEntry_GetObjectByName() {
        // The journal is the same as in TestObjectFactory but should NOT use that journal construction
        // because we need to test that addEntry works before using it in TestObjectFactory methods.
        ObjectJournalEntry m13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournalEntry m31Entry = new ObjectJournalEntry(buildM31Object());
        ObjectJournal objectJournal = new ObjectJournal();
        objectJournal.addEntry(m13Entry);
        objectJournal.addEntry(m31Entry);

        AstronomicalObject freshM13Object = buildM13Object();
        AstronomicalObject firstRetrieved = objectJournal.getEntryByName("M13").getAstronomicalObject();
        Assertions.assertEquals(freshM13Object,firstRetrieved);

        AstronomicalObject freshM31Object = buildM31Object();
        AstronomicalObject secondRetrieved = objectJournal.getEntryByName("M31").getAstronomicalObject();
        Assertions.assertEquals(freshM31Object,secondRetrieved);
    }

    @Test
    public void testGetObjectByNameObjectThrowsExceptionWhenNameMissing() {
        ObjectJournal objectJournal = buildM13M31ObjectJournal();
        Assertions.assertThrows(
                NoSuchEntryException.class,
                () -> objectJournal.getEntryByName("M14")
        );
    }

    @Test
    public void testAddEntryThrowsExceptionOnDuplicateKey() {
        ObjectJournalEntry m13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournal objectJournal = new ObjectJournal();
        objectJournal.addEntry(m13Entry);

        ObjectJournalEntry duplicateEntry = new ObjectJournalEntry(buildM13Object());
        Assertions.assertThrows(
                EntryAlreadyExistsException.class,
                () -> objectJournal.addEntry(duplicateEntry)
        );
    }



    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonObjectJournalNotEqual() {
        ObjectJournal journal = new ObjectJournal();
        String otherObject = "";
        boolean result = journal.equals(otherObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsBothEmptyIsEqual() {
        ObjectJournal journal1 = new ObjectJournal();
        ObjectJournal journal2 = new ObjectJournal();
        boolean result = journal1.equals(journal2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentOneElementNotEqual() {
        ObjectJournal journal1 = new ObjectJournal();
        journal1.addEntry(new ObjectJournalEntry(buildM13Object()));
        ObjectJournal journal2 = new ObjectJournal();
        journal2.addEntry(new ObjectJournalEntry(buildM31Object()));
        boolean result = journal1.equals(journal2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsIdenticalNonemptyIsEqual() {
        ObjectJournal journal1 = new ObjectJournal();
        journal1.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        journal1.addEntry(new ObjectJournalEntry(buildM31Object()));
        ObjectJournal journal2 = new ObjectJournal();
        journal2.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        journal2.addEntry(new ObjectJournalEntry(buildM31Object()));
        boolean result = journal1.equals(journal2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsProperSubsetNotEqual() {
        ObjectJournal subset = new ObjectJournal();
        subset.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        ObjectJournal superset = new ObjectJournal();
        superset.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        superset.addEntry(new ObjectJournalEntry(buildM31Object()));
        boolean result = subset.equals(superset);
        Assertions.assertFalse(result);
    }



    @Test
    public void testRemoveEntryActuallyRemoves() {
        ObjectJournalEntry m13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournalEntry m31Entry = new ObjectJournalEntry(buildM31Object());
        ObjectJournal actual = new ObjectJournal();
        actual.addEntry(m13Entry);
        actual.addEntry(m31Entry);

        ObjectJournalEntry freshM13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(freshM13Entry);

        actual.removeEntry(m31Entry);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testRemoveEntryThrowsNoSuchEntryException() {
        ObjectJournalEntry m13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournalEntry m31Entry = new ObjectJournalEntry(buildM31Object());
        ObjectJournal objectJournal = new ObjectJournal();
        objectJournal.addEntry(m13Entry);

        Assertions.assertThrows(
                NoSuchEntryException.class,
                () -> objectJournal.removeEntry(m31Entry)
        );
    }



    @Test
    public void testRemoveEntryByNameActuallyRemoves() {
        ObjectJournalEntry freshM13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(freshM13Entry);

        ObjectJournal actual = buildM13M31ObjectJournal();
        actual.removeEntryByName("M31");

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testRemoveEntryByNameThrowsNoSuchEntryException() {
        ObjectJournalEntry m13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournal objectJournal = new ObjectJournal();
        objectJournal.addEntry(m13Entry);

        Assertions.assertThrows(
                NoSuchEntryException.class,
                () -> objectJournal.removeEntryByName("M31")
        );
    }



    @Test
    public void testMarkCompleteByNameMarksCompleteM13() {
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        ObjectJournal actual = new ObjectJournal();
        actual.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus()
        ));
        actual.markCompleteByName("M13",LocalDate.parse("2023-01-01"));
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testMarkCompleteByNameMarksCompleteM31() {
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus(LocalDate.parse("2022-12-31"))
        ));
        ObjectJournal actual = new ObjectJournal();
        actual.addEntry(new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus()
        ));
        actual.markCompleteByName("M31",LocalDate.parse("2022-12-31"));
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testMarkCompleteByNameThrowsIfMissing() {
        ObjectJournal emptyJournal = new ObjectJournal();
        Assertions.assertThrows(
            NoSuchEntryException.class,
                () -> emptyJournal.markCompleteByName("M13",LocalDate.parse("2023-01-01"))
        );
    }



    @Test
    public void testMarkIncompleteByNameMarksIncompleteM13() {
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus()
        ));
        ObjectJournal actual = new ObjectJournal();
        actual.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        actual.markIncompleteByName("M13");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testMarkIncompleteByNameMarksIncompleteM31() {
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus()
        ));
        ObjectJournal actual = new ObjectJournal();
        actual.addEntry(new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus(LocalDate.parse("2022-12-31"))
        ));
        actual.markIncompleteByName("M31");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testMarkIncompleteByNameThrowsIfMissing() {
        ObjectJournal emptyJournal = new ObjectJournal();
        Assertions.assertThrows(
                NoSuchEntryException.class,
                () -> emptyJournal.markIncompleteByName("M13")
        );
    }
}
