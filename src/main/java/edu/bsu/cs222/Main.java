package edu.bsu.cs222;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "ask-astraeus",
        description = "Maintain a journal of astronomical objects to view via its subcommands.",
        subcommands = {
                AddCommand.class,
                RemoveCommand.class,
                CompleteCommand.class,
                UncompleteCommand.class,
                ViewCommand.class
        }
)
public class Main {

    // Suppressing this because it is necessary to correctly enable help for PicoCLI. We need this help menu to
    // robustly maintain a list of subcommands, and therefore --help must be used for the main command. However,
    // the variable must necessarily remain unused because there is no logic in the main class (which is the
    // canonical way to require a subcommand with PicoCLI). Therefore, there is no other way around this warning.
    @SuppressWarnings("unused")
    @Option(
            names = {"-h","--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main())
                .setExecutionExceptionHandler(new CLIExceptionMessageHandler())
                .execute(args);
        System.exit(exitCode);
    }
}
