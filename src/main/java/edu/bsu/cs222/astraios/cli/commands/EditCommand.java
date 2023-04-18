package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.model.exceptions.NoSuchEntryException;
import edu.bsu.cs222.astraios.model.journal.ObjectJournal;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "edit",
        description = "Edits the details of an astronomical object in the journal. Not to be used for editing " +
                "completion status of an object!"
    )
    public class EditCommand implements Callable<Integer> {

        @CommandLine.Option(
                names = {"-h", "--help"},
                usageHelp = true,
                description = "Display this help message"
        )
        private boolean helpRequested;

        @CommandLine.Parameters(
                index = "0",
                description = "The name of the object to edit, as stored in the journal"
        )
        private String name;

        @Override
        public Integer call() {
            return null;
        }

        private void verifyName(ObjectJournal objectJournal, String newName) {
        try {
            objectJournal.getEntryByName(newName);
            throw new RuntimeException();
        }
            catch (NoSuchEntryException ignored) {
            }
        }
}
