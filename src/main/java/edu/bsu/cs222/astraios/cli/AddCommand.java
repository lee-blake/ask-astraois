package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.*;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.Callable;

// Suppressing warnings for the Unicode escaping of the degree character in this file because the fix - replacing
// the escape with the literal character - has caused unmappable character errors when running this project
// with default settings. As the inlining of the characters does not impede the functionality, only the readability,
// and as the context indicates it is the degree character, fixing this warning will be delayed until all team members
// can meet and confirm that the change does not cause errors on their respective systems.
@SuppressWarnings("UnnecessaryUnicodeEscape")
@Command(
        name = "add",
        description = "Adds an object to the object journal. The name, right ascension, and declination are required, "
                + "while all other fields are optional. By default, objects are marked as incomplete when they are "
                + "first added."
)
public class AddCommand implements Callable<Integer> {

    // Suppressing this since we do need a variable to trigger this help message annotation
    // but don't need to check it in the logic.
    @SuppressWarnings("unused")
    @Option(
            names = {"-h","--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @Parameters(
            index = "0",
            description = "The name of the object to add, as will be stored in the journal"
    )
    private String name;

    @Option(
           names = {"--ra"},
           required = true,
           description = "The right ascension of the object to add. Accepted forms are:\n"
                   + "\tStandard hour form (22h 30m 26s)",
           converter = CLIHourCoordinateTypeConverter.class
    )
    private HourCoordinate rightAscension;

    @Option(
            names = {"--dec"},
            required = true,
            description = """
                    The declination of the object to add. Accepted forms are:
                    \tStandard degree form (45\u00b0 30' 26")
                    \tAsterisk degree form (45* 30' 26")""",
            converter = CLIHalfCircleDegreeCoordinateTypeConverter.class
    )
    private HalfCircleDegreeCoordinate declination;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException,
            IOException, EntryAlreadyExistsException {
        ListFileMaintainer maintainer = new ListFileMaintainer(
                ListFileMaintainer.defaultOriginalPath,
                ListFileMaintainer.defaultBackupPath
        );
        // We initialize an empty list so that if the file is missing we can still have a list to add to and save to
        // create the previously non-existent file.
        ObjectList objectList = new ObjectList();
        try {
            objectList = maintainer.loadObjectListFromFile();
        }
        catch (NoSuchFileException ignored) {}
        objectList.addEntry(
                new ObjectListEntry(
                        new AstronomicalObject(
                                this.name,
                                new RightAscensionDeclinationCoordinates(
                                        this.rightAscension,
                                        this.declination
                                )
                        ),
                        new CompletionStatus()
                )
        );
        try {
            maintainer.saveObjectListToFile(objectList);
        }
        catch (NoSuchFileException exception) {
            throw new NoSuchFileOnSaveException("Encountered a no such file exception on save!");
        }
        return 0;
    }
}
