package edu.bsu.cs222.astraios.model;

public class EntryAlreadyExistsException extends ObjectJournalEntryException {

    public EntryAlreadyExistsException(String message) {
        super(message);
    }
}
