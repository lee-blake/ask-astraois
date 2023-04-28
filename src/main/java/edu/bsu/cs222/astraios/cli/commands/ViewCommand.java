package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.model.journal.ObjectJournal;
import edu.bsu.cs222.astraios.persistence.CouldNotParseJournalFileException;
import edu.bsu.cs222.astraios.persistence.InvalidJournalFileContentsException;
import edu.bsu.cs222.astraios.persistence.JournalFileMaintainer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(
        name = "view",
        description = "Displays information on objects in the journal."
)
public class ViewCommand implements Callable<Integer> {

    @SuppressWarnings("unused")
    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @SuppressWarnings("unused")
    @Parameters(
            index = "0..*",
            description = "The names of specific objects to display. If none are specified, all objects in the "
                    + "journal are shown."
    )
    private String[] names;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException, IOException {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                JournalFileMaintainer.defaultOriginalPath,
                JournalFileMaintainer.defaultBackupPath
        );
        ObjectJournal objectJournal = maintainer.loadObjectJournalFromFile();
        ObjectJournal.ObjectJournalCLIFormatter journalCLIFormatter = objectJournal.new ObjectJournalCLIFormatter();
        this.printObjects(journalCLIFormatter, names);
        return 0;
    }

    private void printObjects(ObjectJournal.ObjectJournalCLIFormatter journalCLIFormatter, String[] namesToPrint) {
        String toPrint;
        // Unfortunately, we do have to check 'null' because if no arguments are passed to a positional parameter,
        // 'null' is what PicoCLI will set the parameter value to.
        if(namesToPrint == null) {
            toPrint = journalCLIFormatter.getCLIViewString();
        }
        else {
            toPrint = journalCLIFormatter.getCLIViewString(namesToPrint);
        }
        System.out.println(toPrint);
    }
}
