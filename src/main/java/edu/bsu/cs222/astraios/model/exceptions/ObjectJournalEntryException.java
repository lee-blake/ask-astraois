package edu.bsu.cs222.astraios.model.exceptions;

public abstract class ObjectJournalEntryException extends RuntimeException {

    public ObjectJournalEntryException(String message) {
        super(message);
    }
}
