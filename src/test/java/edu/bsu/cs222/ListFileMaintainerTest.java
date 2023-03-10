package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class ListFileMaintainerTest {

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

    public AstronomicalObject buildM13Object() {
        HourCoordinate m13RA = new HourCoordinate(16,41,41.24);
        HalfCircleDegreeCoordinate m13Dec = new HalfCircleDegreeCoordinate(36,27,35.5);
        RightAscensionDeclinationCoordinates m13Coords = new RightAscensionDeclinationCoordinates(m13RA,m13Dec);
        return new AstronomicalObject("M13",m13Coords);
    }

    public AstronomicalObject buildM31Object() {
        HourCoordinate m31RA = new HourCoordinate(0,42,44.30);
        HalfCircleDegreeCoordinate m31Dec = new HalfCircleDegreeCoordinate(41,16,9);
        RightAscensionDeclinationCoordinates m31Coords = new RightAscensionDeclinationCoordinates(m31RA,m31Dec);
        return new AstronomicalObject("M31",m31Coords);
    }

    public ObjectList buildM13M31List() throws ObjectListEntryAlreadyExistsException {
        ObjectList objectList = new ObjectList();
        objectList.addEntry(new ObjectListEntry(
                buildM13Object()
        ));
        objectList.addEntry(new ObjectListEntry(
                buildM31Object()
                ,new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        return objectList;
    }



    @Test
    public void testKeepBackupCopyNeitherPresentNoThrow() throws IOException {
        ListFileMaintainer maintainer = new ListFileMaintainer(tempDir.resolve("original"), tempDir.resolve("backup"));
        maintainer.keepBackupCopy();
    }

    @Test
    public void testKeepBackupCopyOnlyBackupPresentNoRename() throws IOException {
        String contentText = "This is the backup";
        writeFile("backup",contentText);
        ListFileMaintainer maintainer = new ListFileMaintainer(tempDir.resolve("original"), tempDir.resolve("backup"));
        maintainer.keepBackupCopy();
        String backupContents = readFile("backup");
        Assertions.assertEquals(contentText,backupContents);
    }

    @Test
    public void testKeepBackupCopyOnlyOriginalPresentRename() throws IOException {
        String contentText = "This is the original";
        writeFile("original",contentText);
        ListFileMaintainer maintainer = new ListFileMaintainer(tempDir.resolve("original"), tempDir.resolve("backup"));
        maintainer.keepBackupCopy();
        String backupContents = readFile("backup");
        Assertions.assertEquals(contentText,backupContents);
    }

    @Test
    public void testKeepBackupCopyBothPresentReplace() throws IOException {
        String contentText = "This is the original";
        writeFile("original",contentText);
        writeFile("backup","This is the backup");
        ListFileMaintainer maintainer = new ListFileMaintainer(tempDir.resolve("original"), tempDir.resolve("backup"));
        maintainer.keepBackupCopy();
        String backupContents = readFile("backup");
        Assertions.assertEquals(contentText,backupContents);
    }

    @Test
    public void testKeepBackupCopyBothPresentDifferentNamesNoProblems() throws IOException {
        String contentText = "This is the original";
        writeFile("source",contentText);
        writeFile("dest","This is the backup");
        ListFileMaintainer maintainer = new ListFileMaintainer(tempDir.resolve("source"), tempDir.resolve("dest"));
        maintainer.keepBackupCopy();
        String backupContents = readFile("dest");
        Assertions.assertEquals(contentText,backupContents);
    }



    @Test
    public void testSaveToAndLoadFromFileGivesSameList() throws ObjectListEntryAlreadyExistsException, IOException {
        ObjectList originalCopy = buildM13M31List();
        ListFileMaintainer maintainer = new ListFileMaintainer(tempDir.resolve("original"), tempDir.resolve("backup"));
        maintainer.saveObjectListToFile(originalCopy);
        ObjectList freshCopy = buildM13M31List();
        ObjectList loadedFromFile = maintainer.loadObjectListFromFile();
        // Cleanup before the assertion
        File original = new File(tempDir.resolve("original").toUri());
        File backup = new File(tempDir.resolve("backup").toUri());
        boolean deletesOk = original.delete() && backup.delete();
        if(!deletesOk) {
            System.out.println("Cleanup failed for the FI/O integration tests! "
                    + "Consider checking your temp directory and removing manually.");
        }
        Assertions.assertEquals(freshCopy,loadedFromFile);
    }
}
