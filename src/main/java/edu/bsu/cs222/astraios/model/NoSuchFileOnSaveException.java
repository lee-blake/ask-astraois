package edu.bsu.cs222.astraios.model;

import java.nio.file.NoSuchFileException;

public class NoSuchFileOnSaveException extends NoSuchFileException {

    public NoSuchFileOnSaveException(String message) {
        super(message);
    }
}
