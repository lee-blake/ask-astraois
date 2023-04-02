package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static edu.bsu.cs222.astraios.model.TestObjectFactory.ObjectJournals.buildM13M83ObjectJournal;

public class JournalFileMaintainerTest {

    @TempDir
    private static Path tempDir;

    public String readFile(String filename) throws IOException {
        Path filePath = tempDir.resolve(filename);
        byte[] contents = Files.readAllBytes(filePath);
        return new String(contents);
    }

    public void writeFile(String filename, String contents) throws IOException {
        Path filePath = tempDir.resolve(filename);
        File file = new File(filePath.toUri());
        FileWriter writer = new FileWriter(file);
        writer.write(contents);
        writer.close();
        file.deleteOnExit();
    }



    @Test
    public void testKeepBackupCopyNeitherPresentNoThrow() throws IOException {
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                tempDir.resolve("original"),
                tempDir.resolve("backup")
        );
        maintainer.keepBackupCopy();
    }

    @Test
    public void testKeepBackupCopyOnlyBackupPresentNoRename() throws IOException {
        String contentText = "This is the backup";
        writeFile("backup",contentText);
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                tempDir.resolve("original"),
                tempDir.resolve("backup")
        );
        maintainer.keepBackupCopy();
        String backupContents = readFile("backup");
        Assertions.assertEquals(contentText,backupContents);
    }

    @Test
    public void testKeepBackupCopyOnlyOriginalPresentRename() throws IOException {
        String contentText = "This is the original";
        writeFile("original",contentText);
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                tempDir.resolve("original"),
                tempDir.resolve("backup")
        );
        maintainer.keepBackupCopy();
        String backupContents = readFile("backup");
        Assertions.assertEquals(contentText,backupContents);
    }

    @Test
    public void testKeepBackupCopyBothPresentReplace() throws IOException {
        String contentText = "This is the original";
        writeFile("original",contentText);
        writeFile("backup","This is the backup");
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                tempDir.resolve("original"),
                tempDir.resolve("backup")
        );
        maintainer.keepBackupCopy();
        String backupContents = readFile("backup");
        Assertions.assertEquals(contentText,backupContents);
    }

    @Test
    public void testKeepBackupCopyBothPresentDifferentNamesNoProblems() throws IOException {
        String contentText = "This is the original";
        writeFile("source",contentText);
        writeFile("dest","This is the backup");
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                tempDir.resolve("source"),
                tempDir.resolve("dest")
        );
        maintainer.keepBackupCopy();
        String backupContents = readFile("dest");
        Assertions.assertEquals(contentText,backupContents);
    }



    @Test
    public void testSaveToAndLoadFromFileGivesSameJournal()
            throws IOException, InvalidJournalFileContentsException, CouldNotParseJournalFileException {
        ObjectJournal originalCopy = buildM13M83ObjectJournal();
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
                tempDir.resolve("original"),
                tempDir.resolve("backup")
        );
        maintainer.saveObjectJournalToFile(originalCopy);
        ObjectJournal freshCopy = buildM13M83ObjectJournal();
        ObjectJournal loadedFromFile = maintainer.loadObjectJournalFromFile();
        // We need to clean up this test because this is where we don't make the file with to delete on exit flag.
        // This must happen before assert because if assert is incorrect then nothing after will be executed.
        File original = new File(tempDir.resolve("original").toUri());
        File backup = new File(tempDir.resolve("backup").toUri());
        boolean deletesOk = original.delete() && backup.delete();
        if(!deletesOk) {
            System.out.println("Cleanup failed for the FI/O integration tests! "
                    + "Consider checking your temp directory and removing manually.");
        }
        Assertions.assertEquals(freshCopy,loadedFromFile);
    }

    @Test
    public void testSaveObjectJournalToFileThrowsSpecialExceptionWhenFileIsMissing() {
        ObjectJournal journal = new ObjectJournal();
        JournalFileMaintainer maintainer = new JournalFileMaintainer(
            tempDir.resolve("missingDir/unreachable"),
            tempDir.resolve("reachable")
        );
        Assertions.assertThrows(
                NoSuchFileOnSaveException.class,
                () -> maintainer.saveObjectJournalToFile(journal)
        );
    }
}
