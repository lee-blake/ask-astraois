package edu.bsu.cs222.astraios.model.exceptions;

public class EntryAlreadyExistsException extends ObjectJournalEntryException {

    public EntryAlreadyExistsException(String message) {
        super(message);
    }
}
