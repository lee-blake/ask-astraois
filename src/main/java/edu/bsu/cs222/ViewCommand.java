package edu.bsu.cs222;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

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
        // TODO implement the logic and a proper return code
        return null;
    }
}
