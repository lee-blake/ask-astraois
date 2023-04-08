package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.cli.converters.ExampleTextConverter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

import java.util.concurrent.Callable;

@Command(
        name = "example",
        description = "Example gives an examples of how to invoke a subcommand from the command line with " +
                "its variables."
)
public class ExampleCommand implements Callable<Integer> {

    @Spec
    CommandSpec specForThisInvocation;

    @Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Display this help message"
    )
    private boolean helpRequested;

    @Parameters(
            index = "0",
            description = "The subcommand provides examples for"
    )
    private String subcommand;

    @Override
    public Integer call() {
        try {
            this.printExamplesForSubcommand();
            return 0;
        }
        catch(IllegalArgumentException ex) {
            // We could make a "converter" to validate input instead, but this is the way picocli recommends.
            // Also, if we were to implement a different view, there would be no need for this - input could be
            // restricted to a set. Therefore, this message should live here.
            throw new CommandLine.ParameterException(
                    specForThisInvocation.commandLine(),
                    "'"
                            + subcommand
                            + "' is not a recognized subcommand! Use 'ask-astraios --help' for a list of subcommands."
                    );
        }
    }

    private void printExamplesForSubcommand() {
        ExampleTextConverter converter = new ExampleTextConverter();
        String textPrint = converter.getExampleStringForSubcommand(this.subcommand);
        System.out.println(textPrint);
    }
}
