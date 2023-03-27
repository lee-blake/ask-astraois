package edu.bsu.cs222.astraios.cli;

import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "ask-astraios",
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

    @Option(
            names = {"-h","--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        int exitCode = new CommandLine(new Main())
                .setExecutionExceptionHandler(new CLIExceptionMessageHandler())
                .execute(args);
        AnsiConsole.systemUninstall();
        System.exit(exitCode);
    }
}
