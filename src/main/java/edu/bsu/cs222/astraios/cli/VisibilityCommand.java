package edu.bsu.cs222.astraios.cli;


import edu.bsu.cs222.astraios.model.*;
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
            names = {"--longitude"},
            required = true,
            description = """
                    The longitude of the observation site. Accepted forms are:
                    \tStandard degree form (45° 30' 26")
                    \tAsterisk degree form (45* 30' 26")""",
            converter = CLIFullCircleDegreeCoordinateTypeConverter.class
    )
    private FullCircleDegreeCoordinate longitude;

    @Option(
            names = {"--latitude"},
            required = true,
            description = """
                    The latitude of the observation site. Accepted forms are:
                    \tStandard degree form (45° 30' 26")
                    \tAsterisk degree form (45* 30' 26")""",
            converter = CLIHalfCircleDegreeCoordinateTypeConverter.class
    )
    private HalfCircleDegreeCoordinate latitude;

    @Option(
            names = {"--datetime"},
            required = true,
            description = """
                    The date and time the observation will occur at. Accepted forms are:
                    \tISO 8601 (2023-12-31T23:59:59+01:00)""",
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
