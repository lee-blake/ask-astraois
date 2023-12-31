package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.cli.converters.CLIAcceptedFormats;
import edu.bsu.cs222.astraios.cli.converters.CLILocalDateTypeConverter;
import edu.bsu.cs222.astraios.model.journal.ObjectJournal;
import edu.bsu.cs222.astraios.persistence.CouldNotParseJournalFileException;
import edu.bsu.cs222.astraios.persistence.InvalidJournalFileContentsException;
import edu.bsu.cs222.astraios.persistence.JournalFileMaintainer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.Callable;

@Command(
        name = "complete",
        description = "Marks an object in the journal as complete. This does not remove the object from the journal."
)
public class CompleteCommand implements Callable<Integer> {

    @SuppressWarnings("unused")
    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @SuppressWarnings("unused")
    @Parameters(
            index = "0",
            description = "The name of the object to mark complete, as stored in the journal."
    )
    private String name;

    @SuppressWarnings("unused")
    @Option(
            names = {"--on", "--date"},
            description = "The date the completion should be marked for. Defaults to current day if not specified. "
                    + "Accepted forms are:\n"
                    + CLIAcceptedFormats.ACCEPTED_DATE_FORMATS,
            defaultValue = "today",
            converter = CLILocalDateTypeConverter.class
    )
    private LocalDate dateOfCompletion;

    @SuppressWarnings("unused")
    @Option(
            names = {"--force"},
            description = "Whether to ignore an already complete status. This can be used to fix an incorrect date."
    )
    private boolean isForced;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException,
            IOException {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                JournalFileMaintainer.defaultOriginalPath,
                JournalFileMaintainer.defaultBackupPath
        );
        ObjectJournal objectJournal = maintainer.loadObjectJournalFromFile();
        this.completeWithAppropriateMethod(objectJournal);
        maintainer.saveObjectJournalToFile(objectJournal);
        return 0;
    }

    private void completeWithAppropriateMethod(ObjectJournal journal) {
        if(this.isForced) {
            journal.forceCompleteByName(this.name, this.dateOfCompletion);
        }
        else {
            journal.markCompleteByName(this.name, this.dateOfCompletion);
        }
    }
}
