package edu.bsu.cs222.astraios.model;

public class EntryAlreadyIncompleteException extends ObjectJournalEntryException {

    public EntryAlreadyIncompleteException(String message) {
        super(message);
    }
}
