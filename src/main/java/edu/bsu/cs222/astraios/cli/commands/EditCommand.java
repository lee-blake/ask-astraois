package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.cli.converters.CLIAcceptedFormats;
import edu.bsu.cs222.astraios.cli.converters.CLIHalfCircleDegreeCoordinateTypeConverter;
import edu.bsu.cs222.astraios.cli.converters.CLIHourCoordinateTypeConverter;
import edu.bsu.cs222.astraios.cli.exceptions.NothingChangedDuringEditException;
import edu.bsu.cs222.astraios.model.astronomy.HalfCircleDegreeCoordinate;
import edu.bsu.cs222.astraios.model.astronomy.HourCoordinate;
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
        name = "edit",
        description = "Edits the details of an astronomical object in the journal. Not to be used for editing " +
                "completion status of an object!"
    )
public class EditCommand implements Callable<Integer> {

    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @Parameters(
            index = "0",
            description = "The name of the object to edit, as stored in the journal"
    )
    private String name;

    @Option(
            names = {"--new-ra"},
            description = "The new right ascension of the object. Accepted forms are:\n"
                    + CLIAcceptedFormats.ACCEPTED_HOUR_COORDINATE_FORMATS,
            converter = CLIHourCoordinateTypeConverter.class
    )
    private HourCoordinate newRightAscension;

    @Option(
            names = {"--new-dec"},
            description = "The new declination of the object. Accepted forms are:\n"
                    + CLIAcceptedFormats.ACCEPTED_DEGREE_COORDINATE_FORMATS,
            converter = CLIHalfCircleDegreeCoordinateTypeConverter.class
    )
    private HalfCircleDegreeCoordinate newDeclination;

    @Option(
            names = {"--new-name"},
            description = "The new name that the object will have."
    )
    private String newName;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException,
            IOException
    {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                JournalFileMaintainer.defaultOriginalPath,
                JournalFileMaintainer.defaultBackupPath
        );
        ObjectJournal objectJournal = maintainer.loadObjectJournalFromFile();
        this.verifyThatSomethingHasChanged();
        this.editTheObject(objectJournal);
        maintainer.saveObjectJournalToFile(objectJournal);
        return 0;
    }

    private void verifyThatSomethingHasChanged() {
        // If these options are all missing they will all be initialized to null by picocli, so we need to check them,
        // so we can tell the user if nothing actually changed.
        if(
                this.newName == null
                        && this.newDeclination == null
                        && this.newRightAscension == null
        ) {
           throw new NothingChangedDuringEditException("None of these options were used during the edit command.");
        }
    }

    private void editTheObject(ObjectJournal journal) {
        // We need to check that these are not equal to null because that is what picocli initializes them too if those
        // options are not actually present.
        if(newName != null) {
            journal.editNameByName(this.name, this.newName);
        }
        if(newRightAscension != null) {
            journal.editRightAscensionByName(this.name, this.newRightAscension);
        }
        if(newDeclination != null) {
            journal.editDeclinationByName(this.name, this.newDeclination);
        }
    }
}
