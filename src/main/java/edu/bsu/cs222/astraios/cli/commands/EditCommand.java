package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.cli.converters.CLIAcceptedFormats;
import edu.bsu.cs222.astraios.cli.converters.CLIHalfCircleDegreeCoordinateTypeConverter;
import edu.bsu.cs222.astraios.cli.converters.CLIHourCoordinateTypeConverter;
import edu.bsu.cs222.astraios.cli.exceptions.NewNameAlreadyTakenDuringEditException;
import edu.bsu.cs222.astraios.cli.exceptions.NewNameWasOldNameDuringEditException;
import edu.bsu.cs222.astraios.cli.exceptions.NothingChangedDuringEditException;
import edu.bsu.cs222.astraios.model.astronomy.AstronomicalObject;
import edu.bsu.cs222.astraios.model.astronomy.HalfCircleDegreeCoordinate;
import edu.bsu.cs222.astraios.model.astronomy.HourCoordinate;
import edu.bsu.cs222.astraios.model.exceptions.NoSuchEntryException;
import edu.bsu.cs222.astraios.model.journal.ObjectJournal;
import edu.bsu.cs222.astraios.model.journal.ObjectJournalEntry;
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
        this.verifyNewNameIsNotTakenAlready(objectJournal);
        ObjectJournalEntry entryToEdit = objectJournal.getEntryByName(name);
        this.editTheObject(entryToEdit);
        this.updateEntryInJournal(objectJournal, entryToEdit);
        maintainer.saveObjectJournalToFile(objectJournal);
        return 0;
    }

    private void verifyThatSomethingHasChanged() {
        if(
                this.newName == null
                        && this.newDeclination == null
                        && this.newRightAscension == null
        ) {
           throw new NothingChangedDuringEditException("None of these options were used during the edit command.");
        }
    }

    private void updateEntryInJournal(ObjectJournal journal, ObjectJournalEntry entry) {
        journal.removeEntryByName(entry.getName());
        journal.addEntry(entry);
    }

    private void editTheObject(ObjectJournalEntry entry) {
        AstronomicalObject toEdit = entry.getAstronomicalObject();
        if(newName != null) {
            toEdit.editName(newName);
        }
        if(newRightAscension != null) {
            toEdit.editRightAscension(newRightAscension);
        }
        if(newDeclination != null) {
            toEdit.editDeclination(newDeclination);
        }
    }

    private void verifyNewNameIsNotTakenAlready(ObjectJournal journal) {
        if(this.newName == null) {
            return;
        }
        else if(this.name.equals(this.newName)) {
            throw new NewNameWasOldNameDuringEditException(
                    "This new name that was entered, was already the name of the object being added."
            );
        }
        try {
            journal.getEntryByName(this.newName);
            throw new NewNameAlreadyTakenDuringEditException(
                    "This new name was already taken during the edit command."
            );
        }
        catch (NoSuchEntryException ignored) {
            // The name is not taken, so we can deal with this without an exception.

        }
    }
}
