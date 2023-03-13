package edu.bsu.cs222;

import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class CustomExceptionMessageHandler implements IExecutionExceptionHandler {

    @Override
    public int handleExecutionException(Exception ex, CommandLine commandLine, ParseResult parseResult) {
        if(ex instanceof NoSuchFileException) {
            this.printNoSuchFileExceptionMessage(commandLine);
        }
        else if(ex instanceof IOException exception) {
            this.printGenericIOExceptionMessage(exception, commandLine);
        }
        else if (ex instanceof InvalidJournalFileContentsException exception) {
            this.printInvalidJournalFileContentsExceptionMessage(exception,commandLine);
        }
        else if (ex instanceof CouldNotParseJournalFileException exception) {
            this.printCouldNotParseJournalFileExceptionMessage(exception,commandLine);
        }
        else if (ex instanceof NoSuchEntryException exception) {
            this.printNoSuchEntryExceptionMessage(exception,commandLine);
        }
        else {
            ex.printStackTrace();
        }
        return 1;
    }

    private void printNoSuchFileExceptionMessage(CommandLine commandLine) {
        String message = """
            Could not read the journal file because it was missing!
            Consider running the 'add' subcommand to add an object and automatically create the file.""";
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
    }

    private void printGenericIOExceptionMessage(IOException exception, CommandLine commandLine) {
        String message = "The program encountered an error while reading the journal file:\n"
                + exception.getMessage();
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
    }

    private void printInvalidJournalFileContentsExceptionMessage(
            InvalidJournalFileContentsException exception,
            CommandLine commandLine
    ) {
        String message = "The journal file could not be read because it had invalid contents:\n"
                + exception.getMessage();
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
    }

    private void printCouldNotParseJournalFileExceptionMessage(
            CouldNotParseJournalFileException exception,
            CommandLine commandLine
    ) {
        String message = "The journal file could not be read because it could not be parsed as CSV:\n"
                + exception.getMessage();
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
    }


    private void printNoSuchEntryExceptionMessage(NoSuchEntryException exception, CommandLine commandLine) {
        String message = "One or more requested journal entries could not be retrieved:\n"
                + exception.getMessage();
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
    }
}
