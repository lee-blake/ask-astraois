package edu.bsu.cs222;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name="ask-astraeus",
        description="Maintain a list of astronomical objects to view."
)
public class Main {

    // Suppressing this because it is necessary to correctly enable help for PicoCLI. We need this help menu to
    // robustly maintain a list of subcommands, and therefore --help must be used for the main command. However,
    // the variable must necessarily remain unused because there is no logic in the main class (which is the
    // canonical way to require a subcommand with PicoCLI). Therefore, there is no other way around this warning.
    @SuppressWarnings("unused")
    @Option(
            names={"-h","--help"},
            usageHelp=true,
            description="Display this help message"
    )
    private boolean helpRequested;

    public static void main(String[] args) {
        // TODO refactor this later - for easier development only
        // Should use args instead of myArgs
        // try-catch is necessary to be able to get any useful error messages in IntelliJ
        // Also need to capture exit code from execution and exit with it
        String[] myArgs = new String[]{"--help"};
        try {
            new CommandLine(new Main()).execute(myArgs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
