package edu.bsu.cs222;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(
        name = "remove",
        description = "Removes an object from the object journal. This command cannot be undone!"
)
public class RemoveCommand implements Callable<Integer> {

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
            description = "The name of the object to remove, as stored in the journal"
    )
    private String name;

    @Override
    public Integer call() {
        // TODO implement the logic and a proper return code
        return null;
    }
}
