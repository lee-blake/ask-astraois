package edu.bsu.cs222.astraios.model.journal;

import edu.bsu.cs222.astraios.cli.exceptions.NewNameAlreadyTakenDuringEditException;
import edu.bsu.cs222.astraios.model.astronomy.AstronomicalObject;
import edu.bsu.cs222.astraios.model.astronomy.HalfCircleDegreeCoordinate;
import edu.bsu.cs222.astraios.model.astronomy.HourCoordinate;
import edu.bsu.cs222.astraios.model.astronomy.RightAscensionDeclinationCoordinates;
import edu.bsu.cs222.astraios.model.exceptions.EntryAlreadyExistsException;
import edu.bsu.cs222.astraios.model.exceptions.NoSuchEntryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static edu.bsu.cs222.astraios.TestObjectFactory.AstronomicalObjects.buildM13Object;
import static edu.bsu.cs222.astraios.TestObjectFactory.AstronomicalObjects.buildM31Object;
import static edu.bsu.cs222.astraios.TestObjectFactory.ObjectJournals.buildM13M31ObjectJournal;

public class ObjectJournalTest {

    @Test
    public void testAddEntry_GetObjectByNameReturnsAddedEntries() {
        // The journal is the same as in TestObjectFactory but should NOT use that journal construction
        // because we need to test that addEntry works before using it in TestObjectFactory methods.
        ObjectJournalEntry m13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournalEntry m31Entry = new ObjectJournalEntry(buildM31Object());
        ObjectJournal objectJournal = new ObjectJournal();
        objectJournal.addEntry(m13Entry);
        objectJournal.addEntry(m31Entry);

        AstronomicalObject freshM13Object = buildM13Object();
        AstronomicalObject firstRetrieved = objectJournal.getEntryByName("M13").getAstronomicalObject();
        Assertions.assertEquals(freshM13Object, firstRetrieved);

        AstronomicalObject freshM31Object = buildM31Object();
        AstronomicalObject secondRetrieved = objectJournal.getEntryByName("M31").getAstronomicalObject();
        Assertions.assertEquals(freshM31Object, secondRetrieved);
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



    @Test
    public void testRemoveEntryByNamePresentEntryActuallyRemoves() {
        ObjectJournalEntry freshM13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(freshM13Entry);

        ObjectJournal actual = buildM13M31ObjectJournal();
        actual.removeEntryByName("M31");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveEntryByNameMissingEntryThrowsNoSuchEntryException() {
        ObjectJournalEntry m13Entry = new ObjectJournalEntry(buildM13Object());
        ObjectJournal objectJournal = new ObjectJournal();
        objectJournal.addEntry(m13Entry);

        Assertions.assertThrows(
                NoSuchEntryException.class,
                () -> objectJournal.removeEntryByName("M31")
        );
    }



    @Test
    public void testMarkCompleteByNameM13MarksComplete() {
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
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testMarkCompleteByNameM31MarksComplete() {
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
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testMarkCompleteByNameMissingEntryThrowsException() {
        ObjectJournal emptyJournal = new ObjectJournal();
        Assertions.assertThrows(
            NoSuchEntryException.class,
                () -> emptyJournal.markCompleteByName("M13",LocalDate.parse("2023-01-01"))
        );
    }



    @Test
    public void testMarkIncompleteByNameM13MarksIncomplete() {
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
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testMarkIncompleteByNameM31MarksIncomplete() {
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
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testMarkIncompleteByNameMissingEntryThrowsException() {
        ObjectJournal emptyJournal = new ObjectJournal();
        Assertions.assertThrows(
                NoSuchEntryException.class,
                () -> emptyJournal.markIncompleteByName("M13")
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
    public void testEqualsBothEmptyEqual() {
        ObjectJournal journal1 = new ObjectJournal();
        ObjectJournal journal2 = new ObjectJournal();
        boolean result = journal1.equals(journal2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsDifferentSingleEntriesNotEqual() {
        ObjectJournal journal1 = new ObjectJournal();
        journal1.addEntry(new ObjectJournalEntry(buildM13Object()));
        ObjectJournal journal2 = new ObjectJournal();
        journal2.addEntry(new ObjectJournalEntry(buildM31Object()));
        boolean result = journal1.equals(journal2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsIdenticalEntriesEqual() {
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
    public void testForceCompleteByNameM13MarksComplete() {
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
        actual.forceCompleteByName("M13",LocalDate.parse("2023-01-01"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testForceCompleteByNameM31MarksComplete() {
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
        actual.forceCompleteByName("M31",LocalDate.parse("2022-12-31"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testForceCompleteByNameMissingEntryThrowsException() {
        ObjectJournal emptyJournal = new ObjectJournal();
        Assertions.assertThrows(
                NoSuchEntryException.class,
                () -> emptyJournal.forceCompleteByName("M13",LocalDate.parse("2023-01-01"))
        );
    }



    @Test
    public void testForceIncompleteByNameM13MarksIncomplete() {
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
        actual.forceIncompleteByName("M13");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testForceIncompleteByNameM31MarksIncomplete() {
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
        actual.forceIncompleteByName("M31");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testForceIncompleteByNameMissingEntryThrowsException() {
        ObjectJournal emptyJournal = new ObjectJournal();
        Assertions.assertThrows(
                NoSuchEntryException.class,
                () -> emptyJournal.forceIncompleteByName("M13")
        );
    }



    @Test
    public void testObjectNameIsTaken() {
        ObjectJournal objectJournal = new ObjectJournal();
        objectJournal.addEntry(new ObjectJournalEntry(
                buildM13Object())
        );
        boolean result = objectJournal.nameIsTaken("M13");
        Assertions.assertTrue(result);
    }

    @Test
    public void testObjectNameIsNotTaken() {
        ObjectJournal objectJournal = new ObjectJournal();
        objectJournal.addEntry(new ObjectJournalEntry(
                buildM13Object())
        );
        boolean result = objectJournal.nameIsTaken("M30");
        Assertions.assertFalse(result);
    }



    @Test
    public void testEditNameByNameM13NameChanges() {
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(
                new ObjectJournalEntry(
                        buildM31Object(),
                        new CompletionStatus(LocalDate.parse("2023-01-01"))
                )
        );
        expected.addEntry(
                new ObjectJournalEntry(
                        new AstronomicalObject(
                                "m13",
                                new RightAscensionDeclinationCoordinates(
                                        new HourCoordinate(16, 41, 41.24),
                                        new HalfCircleDegreeCoordinate(36, 27, 35.5)
                                )
                        ),
                        new CompletionStatus()
                )
        );
        ObjectJournal actual = buildM13M31ObjectJournal();
        actual.editNameByName("M13", "m13");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEditNameByNameM31NameChanges() {
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(
                new ObjectJournalEntry(
                        new AstronomicalObject(
                                "Andromeda Galaxy",
                                new RightAscensionDeclinationCoordinates(
                                        new HourCoordinate(0, 42, 44.30),
                                        new HalfCircleDegreeCoordinate(41, 16, 9)
                                )
                        ),
                        new CompletionStatus(LocalDate.parse("2023-01-01"))
                )
        );
        expected.addEntry(
                new ObjectJournalEntry(
                        buildM13Object(),
                        new CompletionStatus()
                )
        );
        ObjectJournal actual = buildM13M31ObjectJournal();
        actual.editNameByName("M31", "Andromeda Galaxy");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEditNameByNameAlreadyTakenThrowsException() {
        ObjectJournal journal = buildM13M31ObjectJournal();
        Assertions.assertThrows(
                NewNameAlreadyTakenDuringEditException.class,
                () -> journal.editNameByName("M13", "M31")
        );
    }

    @Test
    public void testEditNameByNameBothSameNoException() {
        ObjectJournal journal = buildM13M31ObjectJournal();
        journal.editNameByName("M13", "M13");
    }



    @Test
    public void testEditRightAscensionDoesNotChange() {
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus()
        ));
        ObjectJournal actual = new ObjectJournal();
        actual.addEntry(new ObjectJournalEntry(
                buildM13Object(),
                new CompletionStatus()
        ));
        actual.editRightAscensionByName("M13", new HourCoordinate(16, 41, 41.24));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEditRightAscensionDoesChange() {
        ObjectJournal expected = new ObjectJournal();
        expected.addEntry(new ObjectJournalEntry(
                new AstronomicalObject(
                        "M31",
                        new RightAscensionDeclinationCoordinates(
                                new HourCoordinate(1, 42, 44.30),
                                new HalfCircleDegreeCoordinate(41, 16, 9)
                        )
                ),
                new CompletionStatus()
        ));
        ObjectJournal actual = new ObjectJournal();
        actual.addEntry(new ObjectJournalEntry(
                buildM31Object(),
                new CompletionStatus()
        ));
        actual.editRightAscensionByName("M31", new HourCoordinate(1, 42, 44.30));
        Assertions.assertEquals(expected, actual);
    }
}
