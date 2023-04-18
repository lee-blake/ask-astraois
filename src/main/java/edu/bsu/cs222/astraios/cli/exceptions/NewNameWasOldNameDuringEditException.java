package edu.bsu.cs222.astraios.cli.exceptions;

public class NewNameWasOldNameDuringEditException extends NewNameAlreadyTakenDuringEditException {

    public NewNameWasOldNameDuringEditException(String message) {
        super(message);

    }
}
