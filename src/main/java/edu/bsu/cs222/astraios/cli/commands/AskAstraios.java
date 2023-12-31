package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.cli.converters.CLIExceptionMessageHandler;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "ask-astraios",
        description = "Maintain a journal of astronomical objects to view via its subcommands.",
        subcommands = {
                AddCommand.class,
                CompleteCommand.class,
                EditCommand.class,
                ExampleCommand.class,
                RemoveCommand.class,
                UncompleteCommand.class,
                ViewCommand.class,
                VisibilityCommand.class
        }
)
public class AskAstraios {

    @SuppressWarnings("unused")
    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        int exitCode = new CommandLine(new AskAstraios())
                .setExecutionExceptionHandler(new CLIExceptionMessageHandler())
                .execute(args);
        AnsiConsole.systemUninstall();
        System.exit(exitCode);
    }
}
