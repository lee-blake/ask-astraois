package edu.bsu.cs222;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

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
           description = "The right ascension of the object to add. Accepted forms are: "
                   + "Fractional hour form (5.894231)",
           converter = FractionalHourTypeConverter.class
    )
    private double rightAscensionAsFractionalHour;

    @Option(
            names = {"--dec"},
            required = true,
            description = "The declination of the object to add. Accepted forms are: "
                    + "Fractional degree form (45.9238553)",
            converter = HalfCircleFractionalDegreeTypeConverter.class
    )
    private double declinationAsFractionalDegree;

    @Override
    public Integer call() {
        // TODO implement the logic and a proper return code
        return null;
    }
}
