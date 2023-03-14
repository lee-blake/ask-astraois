package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.*;
import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class CLIExceptionMessageHandler implements IExecutionExceptionHandler {

    private CommandLine commandLine;

    @Override
    public int handleExecutionException(Exception ex, CommandLine commandLine, ParseResult parseResult) {
        this.commandLine = commandLine;

        if(ex instanceof NoSuchFileOnSaveException) {
            this.printNoSuchFileOnSaveExceptionMessage();
        }
        else if(ex instanceof NoSuchFileException) {
            this.printNoSuchFileExceptionMessage();
        }
        else if(ex instanceof IOException exception) {
            this.printGenericIOExceptionMessage(exception);
        }
        else if(ex instanceof InvalidJournalFileContentsException exception) {
            this.printInvalidJournalFileContentsExceptionMessage(exception);
        }
        else if(ex instanceof CouldNotParseJournalFileException exception) {
            this.printCouldNotParseJournalFileExceptionMessage(exception);
        }
        else if(ex instanceof NoSuchEntryException exception) {
            this.printNoSuchEntryExceptionMessage(exception);
        }
        else if(ex instanceof EntryAlreadyExistsException) {
            this.printEntryAlreadyExistsExceptionMessage();
        }
        else if(ex instanceof EntryAlreadyCompleteException) {
            this.printEntryAlreadyCompleteExceptionMessage();
        }
        else if(ex instanceof EntryAlreadyIncompleteException) {
            this.printEntryAlreadyIncompleteExceptionMessage();
        }
        else {
            ex.printStackTrace();
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

    private void printNoSuchFileOnSaveExceptionMessage() {
        String message = """
            Could not save the journal file because it was missing!
            This usually occurs when the 'data' directory is missing or has bad write permissions.""";
        this.printAsErrorText(message);
    }

    private void printNoSuchFileExceptionMessage() {
        String message = """
            Could not read the journal file because it was missing!
            Consider running the 'add' subcommand to add an object and automatically create the file.""";
        this.printAsErrorText(message);
    }

    private void printGenericIOExceptionMessage(IOException exception) {
        String message = "The program encountered an error while reading the journal file:\n"
                + exception.getMessage();
        this.printAsErrorText(message);
    }

    private void printInvalidJournalFileContentsExceptionMessage(InvalidJournalFileContentsException exception) {
        String message = "The journal file could not be read because it had invalid contents:\n"
                + exception.getMessage();
        this.printAsErrorText(message);
    }

    private void printCouldNotParseJournalFileExceptionMessage(CouldNotParseJournalFileException exception) {
        String message = "The journal file could not be read because it could not be parsed as CSV:\n"
                + exception.getMessage();
        this.printAsErrorText(message);
    }

    private void printNoSuchEntryExceptionMessage(NoSuchEntryException exception) {
        String message = "One or more requested journal entries could not be retrieved:\n"
                + exception.getMessage();
        this.printAsErrorText(message);
    }

    private void printEntryAlreadyExistsExceptionMessage() {
        String message = "The new object could not be added because "
                + "an entry of that name already exists in the journal!";
        this.printAsErrorText(message);
    }

    private void printEntryAlreadyCompleteExceptionMessage() {
        String message = "The object could not be marked as complete because it is already complete in the journal!";
        this.printAsErrorText(message);
    }

    private void printEntryAlreadyIncompleteExceptionMessage() {
        String message = "The object could not be marked as incomplete because "
                + "it is already incomplete in the journal!";
        this.printAsErrorText(message);
    }
}
