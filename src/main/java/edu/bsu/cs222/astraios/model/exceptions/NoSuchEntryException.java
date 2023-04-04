package edu.bsu.cs222.astraios.model.exceptions;

public class NoSuchEntryException extends ObjectJournalEntryException {

    public NoSuchEntryException(String message) {
        super(message);
    }
}
