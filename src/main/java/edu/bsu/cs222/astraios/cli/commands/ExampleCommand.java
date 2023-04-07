package edu.bsu.cs222.astraios.cli.commands;

import edu.bsu.cs222.astraios.cli.converters.ExampleTextConverter;
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
        ExampleTextConverter converter = new ExampleTextConverter();
        String textPrint = converter.getExampleStringForSubcommand(subcommand);
        System.out.println(textPrint);
        return 0;
    }
}
