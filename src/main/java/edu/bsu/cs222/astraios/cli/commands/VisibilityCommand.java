package edu.bsu.cs222.astraios.cli.commands;


import edu.bsu.cs222.astraios.cli.converters.CLIAcceptedFormats;
import edu.bsu.cs222.astraios.cli.converters.CLIFullCircleDegreeCoordinateTypeConverter;
import edu.bsu.cs222.astraios.cli.converters.CLIHalfCircleDegreeCoordinateTypeConverter;
import edu.bsu.cs222.astraios.cli.converters.CLIOffsetDateTimeTypeConverter;
import edu.bsu.cs222.astraios.model.astronomy.*;
import edu.bsu.cs222.astraios.model.journal.ObjectJournal;
import edu.bsu.cs222.astraios.persistence.CouldNotParseJournalFileException;
import edu.bsu.cs222.astraios.persistence.InvalidJournalFileContentsException;
import edu.bsu.cs222.astraios.persistence.JournalFileMaintainer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.concurrent.Callable;

@Command(
        name = "check-visibility",
        description = "Checks whether an object will be visible in a given date, time, and location. If so, the "
                + "program displays its predicted altitude and azimuth. This command accounts for the sun but not "
                + " for weather or any obstructions on the horizon."
)
public class VisibilityCommand implements Callable<Integer> {

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
            description = "The name of the object to add, as will be stored in the journal"
    )
    private String name;

    @SuppressWarnings("unused")
    @Option(
            names = {"--longitude"},
            required = true,
            description = "The longitude of the observation site. Accepted forms are:\n"
                    + CLIAcceptedFormats.ACCEPTED_DEGREE_COORDINATE_FORMATS,
            converter = CLIFullCircleDegreeCoordinateTypeConverter.class
    )
    private FullCircleDegreeCoordinate longitude;

    @SuppressWarnings("unused")
    @Option(
            names = {"--latitude"},
            required = true,
            description = "The latitude of the observation site. Accepted forms are:\n"
                    + CLIAcceptedFormats.ACCEPTED_DEGREE_COORDINATE_FORMATS,
            converter = CLIHalfCircleDegreeCoordinateTypeConverter.class
    )
    private HalfCircleDegreeCoordinate latitude;

    @SuppressWarnings("unused")
    @Option(
            names = {"--datetime"},
            required = true,
            description = "The date and time the observation will occur at. Accepted forms are:\n"
                    + CLIAcceptedFormats.ACCEPTED_DATETIME_FORMATS,
            converter = CLIOffsetDateTimeTypeConverter.class
    )
    private OffsetDateTime datetime;

    @Override
    public Integer call() throws InvalidJournalFileContentsException, CouldNotParseJournalFileException, IOException {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                JournalFileMaintainer.defaultOriginalPath,
                JournalFileMaintainer.defaultBackupPath
        );
        ObjectJournal objectJournal = maintainer.loadObjectJournalFromFile();
        AstronomicalObject objectToCheck = objectJournal.getEntryByName(name).getAstronomicalObject();
        Observation observation = new Observation(
                new LongitudeLatitudeCoordinates(
                        longitude,
                        latitude
                ),
                datetime
        );
        this.outputVisibilityStatusString(objectToCheck, observation);
        return 0;
    }

    private void outputVisibilityStatusString(AstronomicalObject objectToCheck, Observation observation) {
        VisibilityChecker visibilityChecker = new VisibilityChecker(objectToCheck, observation);
        String stringToOutput = visibilityChecker.buildVisibilityStatusString();
        System.out.println(stringToOutput);
    }
}
