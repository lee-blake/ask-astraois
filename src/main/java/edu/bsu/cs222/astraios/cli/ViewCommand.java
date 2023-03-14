package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(
        name = "view",
        description = "Displays information on objects in the journal."
)
public class ViewCommand implements Callable<Integer> {

    // Suppressing this since we do need a variable to trigger this help message annotation
    // but don't need to check it in the logic.
    @SuppressWarnings("unused")
    @CommandLine.Option(
            names = {"-h","--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @Parameters(
            index = "0..*",
            description = "The names of specific objects to display. If none are specified, all objects in the "
                    + "journal are shown."
    )
    private String[] names;

    @Override
    public Integer call() throws IOException, InvalidJournalFileContentsException,
            CouldNotParseJournalFileException, NoSuchEntryException {
        ListFileMaintainer maintainer = new ListFileMaintainer(
                ListFileMaintainer.defaultOriginalPath,
                ListFileMaintainer.defaultBackupPath
        );
        ObjectList objectList = maintainer.loadObjectListFromFile();
        this.printObjects(objectList,names);
        return 0;
    }

    private void printObjects(ObjectList objectList, String[] namesToPrint) throws NoSuchEntryException {
        ObjectList.ObjectListCLIFormatter formatter = objectList.new ObjectListCLIFormatter();
        String toPrint;
        if (objectList.equals(new ObjectList())) {
            toPrint = "The journal currently contains no entries. Please use the 'add' subcommand "
                    + "to add objects to the journal.";
        }
        // Unfortunately, we do have to check 'null' because if no arguments are passed to a positional parameter,
        // 'null' is what PicoCLI will set the parameter value to.
        else if(namesToPrint == null) {
            toPrint = formatter.getCLIViewString();
        }
        else {
            toPrint = formatter.getCLIViewString(namesToPrint);
        }
        System.out.println(toPrint);
    }
}
