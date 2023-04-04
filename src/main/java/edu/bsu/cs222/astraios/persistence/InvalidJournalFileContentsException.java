package edu.bsu.cs222.astraios.persistence;

public class InvalidJournalFileContentsException extends Exception {

    public InvalidJournalFileContentsException(String message) {
        super(message);
    }
}
