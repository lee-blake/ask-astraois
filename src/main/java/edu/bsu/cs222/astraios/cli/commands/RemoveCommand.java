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
        name = "remove",
        description = "Removes an object from the object journal. This command cannot be undone!"
)
public class RemoveCommand implements Callable<Integer> {

    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @Parameters(
            index = "0",
            description = "The name of the object to remove, as stored in the journal"
    )
    private String name;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException, IOException {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                JournalFileMaintainer.defaultOriginalPath,
                JournalFileMaintainer.defaultBackupPath
        );
        ObjectJournal objectJournal = maintainer.loadObjectJournalFromFile();
        objectJournal.removeEntryByName(name);
        maintainer.saveObjectJournalToFile(objectJournal);
        return 0;
    }
}
