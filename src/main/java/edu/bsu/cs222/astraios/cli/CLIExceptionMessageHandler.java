package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.*;
import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class CLIExceptionMessageHandler implements IExecutionExceptionHandler {

    @Override
    public int handleExecutionException(Exception ex, CommandLine commandLine, ParseResult parseResult) {
        if(ex instanceof NoSuchFileOnSaveException) {
            this.printNoSuchFileOnSaveExceptionMessage(commandLine);
        }
        else if(ex instanceof NoSuchFileException) {
            this.printNoSuchFileExceptionMessage(commandLine);
        }
        else if(ex instanceof IOException exception) {
            this.printGenericIOExceptionMessage(exception, commandLine);
        }
        else if(ex instanceof InvalidJournalFileContentsException exception) {
            this.printInvalidJournalFileContentsExceptionMessage(exception,commandLine);
        }
        else if(ex instanceof CouldNotParseJournalFileException exception) {
            this.printCouldNotParseJournalFileExceptionMessage(exception,commandLine);
        }
        else if(ex instanceof NoSuchEntryException exception) {
            this.printNoSuchEntryExceptionMessage(exception,commandLine);
        }
        else if(ex instanceof EntryAlreadyExistsException) {
            this.printEntryAlreadyExistsExceptionMessage(commandLine);
        }
        else if(ex instanceof EntryAlreadyCompleteException) {
            this.printEntryAlreadyCompleteExceptionMessage(commandLine);
        }
        else if(ex instanceof EntryAlreadyIncompleteException) {
            this.printEntryAlreadyIncompleteExceptionMessage(commandLine);
        }
        else {
            ex.printStackTrace();
        }
        return 1;
    }

    private void printNoSuchFileOnSaveExceptionMessage(CommandLine commandLine) {
        String message = """
            Could not save the journal file because it was missing!
            This usually occurs when the 'data' directory is missing or has bad write permissions.""";
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
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

    private void printEntryAlreadyExistsExceptionMessage(CommandLine commandLine) {
        String message = "The new object could not be added because "
                + "an entry of that name already exists in the journal!";
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
    }


    private void printEntryAlreadyCompleteExceptionMessage(CommandLine commandLine) {
        String message = "The object could not be marked as complete because it is already complete in the journal!";
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
    }


    private void printEntryAlreadyIncompleteExceptionMessage(CommandLine commandLine) {
        String message = "The object could not be marked as incomplete because "
                + "it is already incomplete in the journal!";
        commandLine.getErr()
                .println(
                        commandLine.getColorScheme()
                                .errorText(message)
                );
    }
}
