package edu.bsu.cs222.astraios.model.journal;

import edu.bsu.cs222.astraios.TestObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectJournalCLIFormatterTest {

    @Test
    public void testGetCLIViewStringEmptyJournalBuildsCorrectly() {
        ObjectJournal emptyJournal = new ObjectJournal();
        ObjectJournal.ObjectJournalCLIFormatter formatter = emptyJournal.new ObjectJournalCLIFormatter();
        String actual = formatter.getCLIViewString();
        String expected = "The journal currently contains no entries. Please use the 'add' subcommand "
                + "to add objects to the journal.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetCLIViewStringSimpleJournalBuildsCorrectly() {
        ObjectJournal simpleJournal = TestObjectFactory.ObjectJournals.buildM13M31ObjectJournal();
        ObjectJournal.ObjectJournalCLIFormatter formatter = simpleJournal.new ObjectJournalCLIFormatter();
        String actual = formatter.getCLIViewString();
        String expected = """
                Name              R.A.          Dec.           Completed\s
                M13               16h41m41.2s   +36°27'35.5"            \s
                M31               00h42m44.3s   +41°16'09.0"   2023-01-01
                """;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetCLIViewStringSimpleJournalSelectionBuildsCorrectly() {
        ObjectJournal simpleJournal = TestObjectFactory.ObjectJournals.buildM13M31ObjectJournal();
        ObjectJournal.ObjectJournalCLIFormatter formatter = simpleJournal.new ObjectJournalCLIFormatter();
        String[] query = new String[]{"M13"};
        String actual = formatter.getCLIViewString(query);
        String expected = """
                Name              R.A.          Dec.           Completed\s
                M13               16h41m41.2s   +36°27'35.5"            \s
                """;
        Assertions.assertEquals(expected, actual);
    }
}
