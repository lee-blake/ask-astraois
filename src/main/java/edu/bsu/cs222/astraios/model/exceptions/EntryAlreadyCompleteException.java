package edu.bsu.cs222.astraios.model.exceptions;

public class EntryAlreadyCompleteException extends ObjectJournalEntryException {

    public EntryAlreadyCompleteException(String message) {
        super(message);
    }
}
