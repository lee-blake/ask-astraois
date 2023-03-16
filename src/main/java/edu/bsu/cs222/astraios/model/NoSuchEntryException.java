package edu.bsu.cs222.astraios.model;

public class NoSuchEntryException extends ObjectJournalEntryException {

    public NoSuchEntryException(String message) {
        super(message);
    }
}
