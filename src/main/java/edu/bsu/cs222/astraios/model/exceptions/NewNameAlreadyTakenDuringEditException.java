package edu.bsu.cs222.astraios.model.exceptions;

public class NewNameAlreadyTakenDuringEditException extends ObjectJournalEntryException {

    public NewNameAlreadyTakenDuringEditException(String message) {
        super(message);
    }
}
