package edu.bsu.cs222;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.time.LocalDate;
import java.util.concurrent.Callable;

@Command(
        name = "complete",
        description = "Marks an object in the journal as complete. This does not remove the object from the journal."
)
public class CompleteCommand implements Callable<Integer> {

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
            description = "The name of the object to mark complete, as stored in the journal"
    )
    private String name;

    @Option(
            names = {"--on"},
            description = "The date the completion should be marked for. Defaults to current day if not specified.",
            defaultValue = "today",
            converter = CLILocalDateTypeConverter.class
    )
    private LocalDate date;

    @Override
    public Integer call() {
        // TODO implement the logic and a proper return code
        return null;
    }
}
