package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.cli.converters.ExampleTextConverter;
import edu.bsu.cs222.astraios.cli.exceptions.InvalidSubcommandForExampleException;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(
        name = "example",
        description = "Example gives an examples of how to invoke a subcommand from the command line with " +
                "its variables."
)
public class ExampleCommand implements Callable<Integer> {

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
            // While we could make a "converter" to throw an exception if the subcommand isn't valid, that would
            // cause duplication and would be less recommended by picocli. This is only necessary because picocli
            // has no option like Python's 'argparse' to restrict a string argument to a specific set.
            throw new InvalidSubcommandForExampleException(
                    "Argument '"
                            + subcommand
                            + "' is not a recognized subcommand!"
            );
        }
    }

    private void printExamplesForSubcommand() {
        ExampleTextConverter converter = new ExampleTextConverter();
        String textPrint = converter.getExampleStringForSubcommand(this.subcommand);
        System.out.println(textPrint);
    }
}
