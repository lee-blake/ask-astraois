package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.*;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.Callable;

@Command(
        name = "add",
        description = "Adds an object to the object journal. The name, right ascension, and declination are required, "
                + "while all other fields are optional. By default, objects are marked as incomplete when they are "
                + "first added."
)
public class AddCommand implements Callable<Integer> {

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
           description = "Required. The right ascension of the object to add. Accepted forms are:\n"
                   + CLIAcceptedFormats.ACCEPTED_HOUR_COORDINATE_FORMATS,
           converter = CLIHourCoordinateTypeConverter.class
    )
    private HourCoordinate rightAscension;

    @Option(
            names = {"--dec"},
            required = true,
            description = "Required. The declination of the object to add. Accepted forms are:\n"
                    + CLIAcceptedFormats.ACCEPTED_DEGREE_COORDINATE_FORMATS,
            converter = CLIHalfCircleDegreeCoordinateTypeConverter.class
    )
    private HalfCircleDegreeCoordinate declination;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException, IOException {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                JournalFileMaintainer.defaultOriginalPath,
                JournalFileMaintainer.defaultBackupPath
        );
        // We initialize an empty journal so that if the file is missing we can still have a journal to add to and save
        // to create the previously non-existent file.
        ObjectJournal objectJournal = new ObjectJournal();
        try {
            objectJournal = maintainer.loadObjectJournalFromFile();
        }
        catch (NoSuchFileException ignored) {}
        objectJournal.addEntry(
                new ObjectJournalEntry(
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
            maintainer.saveObjectJournalToFile(objectJournal);
        }
        catch (NoSuchFileException saveException) {
            throw new NoSuchFileOnSaveException("Encountered a no such file exception on save!");
        }
        return 0;
    }
}
