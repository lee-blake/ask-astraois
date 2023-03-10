package edu.bsu.cs222;

import java.io.IOException;
import java.nio.file.*;

public class ListFileMaintainer {

    private final Path originalFilePath;
    private final Path backupFilePath;

    public ListFileMaintainer(Path originalPath, Path backupPath) {
        this.originalFilePath = originalPath;
        this.backupFilePath = backupPath;
    }

    public void keepBackupCopy() throws IOException {
        try {
            Files.move(this.originalFilePath, this.backupFilePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(NoSuchFileException exception) {
            // Do nothing - if the original does not exist to begin with, we will be able to write safely and
            // so the goal of this method has already been accomplished
        }
    }

    private String readFileToString() throws IOException {
        return Files.readString(this.originalFilePath);
    }

    private void writeStringToFile(String fileContents) throws IOException {
        Files.writeString(this.originalFilePath, fileContents);
    }
}
