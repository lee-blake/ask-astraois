package edu.bsu.cs222;

import java.nio.file.NoSuchFileException;

public class NoSuchFileOnSaveException extends NoSuchFileException {

    public NoSuchFileOnSaveException(String message) {
        super(message);
    }
}
