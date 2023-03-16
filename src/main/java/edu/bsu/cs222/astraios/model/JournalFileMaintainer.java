package edu.bsu.cs222.astraios.model;

import java.io.IOException;
import java.nio.file.*;

public class JournalFileMaintainer {

    public static Path defaultOriginalPath = Paths.get("data/AstraiosJournal.csv");
    public static Path defaultBackupPath = Paths.get("data/AstraiosJournal.csv.backup");
    public static final Header[] canonicalHeaderOrder = new Header[]{
            Header.NAME,
            Header.RIGHT_ASCENSION,
            Header.DECLINATION,
            Header.COMPLETION_DATE
    };

    private final Path originalFilePath;
    private final Path backupFilePath;

    public JournalFileMaintainer(Path originalPath, Path backupPath) {
        this.originalFilePath = originalPath;
        this.backupFilePath = backupPath;
    }

    public void saveObjectJournalToFile(ObjectJournal journalToSave) throws IOException {
        CSVConverter converter = new CSVConverter();
        String fileContents = converter.convertObjectJournalToCSV(journalToSave,canonicalHeaderOrder);
        this.keepBackupCopy();
        this.writeStringToFile(fileContents);
    }

    protected void keepBackupCopy() throws IOException {
        try {
            Files.move(this.originalFilePath, this.backupFilePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(NoSuchFileException exception) {
            // Do nothing - if the original does not exist to begin with, we will be able to write safely and
            // so the goal of this method has already been accomplished.
        }
    }

    private void writeStringToFile(String fileContents) throws IOException {
        Files.writeString(this.originalFilePath, fileContents,StandardOpenOption.CREATE);
    }

    public ObjectJournal loadObjectJournalFromFile()
            throws CouldNotParseJournalFileException, IOException, InvalidJournalFileContentsException {
        String fileCSV = this.readFileToString();
        CSVConverter converter = new CSVConverter();
        return converter.buildObjectJournalFromCSV(fileCSV);
    }

    private String readFileToString() throws IOException {
        return Files.readString(this.originalFilePath);
    }
}
