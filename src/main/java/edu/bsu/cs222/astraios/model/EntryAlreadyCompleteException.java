package edu.bsu.cs222.astraios.model;

public class EntryAlreadyCompleteException extends ObjectJournalEntryException {

    public EntryAlreadyCompleteException(String message) {
        super(message);
    }
}
