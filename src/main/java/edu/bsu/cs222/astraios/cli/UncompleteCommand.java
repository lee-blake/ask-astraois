package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.*;
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
            names = {"-h","--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @Parameters(
            index = "0",
            description = "The name of the object to mark incomplete, as stored in the journal"
    )
    private String name;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException, IOException {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                JournalFileMaintainer.defaultOriginalPath,
                JournalFileMaintainer.defaultBackupPath
        );
        ObjectJournal objectJournal = maintainer.loadObjectJournalFromFile();
        objectJournal.markIncompleteByName(name);
        maintainer.saveObjectJournalToFile(objectJournal);
        return 0;
    }
}
