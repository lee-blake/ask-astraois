package edu.bsu.cs222.astraios.model;

public class EntryAlreadyExistsException extends ObjectListEntryException {

    public EntryAlreadyExistsException(String message) {
        super(message);
    }
}
