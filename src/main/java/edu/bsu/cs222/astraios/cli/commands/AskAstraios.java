package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.cli.converters.CLIExceptionMessageHandler;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.ScopeType;

import java.util.List;

@Command(
        name = "ask-astraios",
        description = "Maintain a journal of astronomical objects to view via its subcommands.",
        subcommands = {
                AddCommand.class,
                CompleteCommand.class,
                ExampleCommand.class,
                RemoveCommand.class,
                UncompleteCommand.class,
                ViewCommand.class,
                VisibilityCommand.class
        }
)
public class AskAstraios {

    private CommandLine commandLine;

    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @Option(
            names = {"--example"},
            description = "Get examples for the subcommand. Equivalent to 'example <subcommand>'. "
                    + "Must be run with a subcommand.",
            scope = ScopeType.INHERIT,
            help = true
    )
    private boolean exampleRequested;

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        AskAstraios astraios = new AskAstraios();
        astraios.commandLine = new CommandLine(astraios)
                .setExecutionExceptionHandler(new CLIExceptionMessageHandler());
        ParseResult parseResult = astraios.commandLine.parseArgs(args);
        int exitCode;
        if(astraios.exampleRequested) {
            exitCode = astraios.executeExampleOption(parseResult);
        }
        else {
            exitCode = astraios.executeNormalLogic(args);
        }
        AnsiConsole.systemUninstall();
        System.exit(exitCode);
    }

    private int executeExampleOption(ParseResult parseResult) {
        List<CommandLine.ParseResult> subcommandsParsed = parseResult.subcommands();
        if(subcommandsParsed.isEmpty()) {
            this.commandLine.getErr().println(
                    "The '--example' option must be run with a valid subcommand! "
                    + "See --help for a list of subcommands.");
            return 1;
        }
        else {
            // Currently, the program only has one layer of subcommands. We may therefore assume that this
            // list is of length 1 and simply unwrap it to a single String. If this changes, the necessary refactor
            // of ExampleTextConverter will necessarily have it take an array of Strings. In that case, we will
            // refactor this to convert the array of subcommands to an array of Strings before right-joining the
            // resulting array of name Strings to ["example"].
            String subcommandWhereThisWasCalledFrom = subcommandsParsed.get(0).commandSpec().name();
            String[] argsToExecuteExampleCorrectly = new String[]{"example", subcommandWhereThisWasCalledFrom};
            return this.commandLine.execute(argsToExecuteExampleCorrectly);
        }
    }

    private int executeNormalLogic(String[] args) {
        return this.commandLine.execute(args);
    }
}
