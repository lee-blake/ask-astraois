package edu.bsu.cs222;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
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
    public Integer call() {
        ListFileMaintainer maintainer = new ListFileMaintainer(
                ListFileMaintainer.defaultOriginalPath,
                ListFileMaintainer.defaultBackupPath
        );
        try {
            ObjectList objectList = maintainer.loadObjectListFromFile();
            if (objectList.equals(new ObjectList())){
                System.out.println("The journal currently contains no entries. Please use the 'add' subcommand "
                        +"to add objects to the journal.");
                return 0;
            }
            this.printObjects(objectList,names);
        }
        catch(NoSuchFileException e) {
            System.out.println(
                    "The journal file could not be read because the file was missing! Please run the 'add' "
                            + "subcommand to automatically create the journal file with an entry."
            );
        }
        catch (InvalidJournalFileContentsException e) {
            System.out.println(
                    "The journal file could not be read because it contained invalid contents:\n"
                            + e.getMessage()
            );
        }
        catch (NoSuchEntryException e) {
            System.out.println(
                    "One or more of the requested entries could not be found:\n" + e.getMessage()
            );
        }
        catch (CouldNotParseJournalFileException e) {
            System.out.println(
                    "The journal file could not be read because the file could not be parsed as CSV!"
            );
        }
        catch (IOException e) {
            System.out.println(
                    "Something went wrong with reading the journal file:\n" + e.getMessage()
            );
        }
        // TODO revisit this value once testing is done
        return 0;
    }

    private void printObjects(ObjectList objectList, String[] namesToPrint) throws NoSuchEntryException {
        ObjectList.ObjectListCLIFormatter formatter = objectList.new ObjectListCLIFormatter();
        String toPrint;
        // Unfortunately, we do have to check 'null' because if no arguments are passed to a positional parameter,
        // that is what PicoCLI with set the parameter value to.
        if(namesToPrint == null) {
            toPrint = formatter.getCLIViewString();
        }
        else {
            toPrint = formatter.getCLIViewString(namesToPrint);
        }
        System.out.println(toPrint);
    }
}
