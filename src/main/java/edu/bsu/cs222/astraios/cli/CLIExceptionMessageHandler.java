package edu.bsu.cs222.astraios.cli;

import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

public class CLIExceptionMessageHandler implements IExecutionExceptionHandler {

    private CommandLine commandLine;

    @Override
    public int handleExecutionException(Exception ex, CommandLine commandLine, ParseResult parseResultUnused) {
        this.commandLine = commandLine;
        try {
            CLIExceptionMessageFactory messageFactory = new CLIExceptionMessageFactory();
            String message =messageFactory.translateExceptionToErrorMessage(ex);
            this.printAsErrorText(message);
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
        return 1;
    }

    private void printAsErrorText(String message) {
        this.commandLine.getErr()
                .println(
                        this.commandLine.getColorScheme()
                                .errorText(message)
                );
    }
}
