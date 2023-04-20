package edu.bsu.cs222.astraios.cli.converters;

import edu.bsu.cs222.astraios.model.exceptions.*;
import edu.bsu.cs222.astraios.persistence.CouldNotParseJournalFileException;
import edu.bsu.cs222.astraios.persistence.InvalidJournalFileContentsException;
import edu.bsu.cs222.astraios.persistence.NoSuchFileOnSaveException;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class CLIExceptionMessageFactory {

    public String translateExceptionToErrorMessage(Exception ex) throws Exception {
        if(ex instanceof NoSuchFileOnSaveException) {
            return """
                    Could not save the journal file because it was missing!
                    This usually occurs when the 'data' directory is missing or has bad write permissions.""";
        }
        else if(ex instanceof NoSuchFileException) {
            return """
                    Could not read the journal file because it was missing!
                    Consider running the 'add' subcommand to add an object and automatically create the file.""";
        }
        else if(ex instanceof IOException exception) {
            return "The program encountered an error while reading the journal file:\n"
                    + exception.getMessage();
        }
        else if(ex instanceof InvalidJournalFileContentsException exception) {
            return "The journal file could not be read because it had invalid contents:\n"
                    + exception.getMessage();
        }
        else if(ex instanceof CouldNotParseJournalFileException exception) {
            return "The journal file could not be read because it could not be parsed as CSV:\n"
                    + exception.getMessage();
        }
        else if(ex instanceof NoSuchEntryException exception) {
            return "One or more requested journal entries could not be retrieved:\n"
                    + exception.getMessage();
        }
        else if(ex instanceof EntryAlreadyExistsException) {
            return "The new object could not be added because "
                    + "an entry of that name already exists in the journal!";
        }
        else if(ex instanceof EntryAlreadyCompleteException) {
            return "The object could not be marked as complete because it is already complete in the journal!";
        }
        else if(ex instanceof EntryAlreadyIncompleteException) {
            return "The object could not be marked as incomplete because "
                    + "it is already incomplete in the journal!";
        }
        else if(ex instanceof NewNameAlreadyTakenDuringEditException) {
            return "The object could not be edited because the new name is already taken "
                    + "by a different object in the journal";
        }
        throw ex;
    }
}
