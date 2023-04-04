package edu.bsu.cs222.astraios.model.exceptions;

public class EntryAlreadyIncompleteException extends ObjectJournalEntryException {

    public EntryAlreadyIncompleteException(String message) {
        super(message);
    }
}
