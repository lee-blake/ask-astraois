package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.model.journal.ObjectJournal;
import edu.bsu.cs222.astraios.persistence.CouldNotParseJournalFileException;
import edu.bsu.cs222.astraios.persistence.InvalidJournalFileContentsException;
import edu.bsu.cs222.astraios.persistence.JournalFileMaintainer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(
        name = "uncomplete",
        description = "Marks an object in the journal as incomplete."
)
public class UncompleteCommand implements Callable<Integer> {

    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @Parameters(
            index = "0",
            description = "The name of the object to mark incomplete, as stored in the journal"
    )
    private String name;

    @Option(
            names = {"--force"},
            description = "Whether to ignore an already incomplete status."
    )
    private boolean isForced;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException, IOException {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                JournalFileMaintainer.defaultOriginalPath,
                JournalFileMaintainer.defaultBackupPath
        );
        ObjectJournal objectJournal = maintainer.loadObjectJournalFromFile();
        this.uncompleteWithAppropriateMethod(objectJournal);
        maintainer.saveObjectJournalToFile(objectJournal);
        return 0;
    }

    private void uncompleteWithAppropriateMethod(ObjectJournal journal) {
        if(this.isForced) {
            journal.forceIncompleteByName(this.name);
        }
        else {
            journal.markIncompleteByName(this.name);
        }
    }
}
