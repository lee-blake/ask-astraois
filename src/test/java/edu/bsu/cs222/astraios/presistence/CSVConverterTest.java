package edu.bsu.cs222.astraios.presistence;

import edu.bsu.cs222.astraios.model.journal.Header;
import edu.bsu.cs222.astraios.model.journal.ObjectJournal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static edu.bsu.cs222.astraios.TestObjectFactory.ObjectJournals.buildM13M31ObjectJournal;

public class CSVConverterTest {

    @Test
    public void testConvertObjectJournalToCSVEmptyJournalCanonicalOrder() throws IOException {
        CSVConverter converter = new CSVConverter();
        ObjectJournal emptyJournal = new ObjectJournal();
        Header[] canonicalHeadersNoCompletion = new Header[]{
                Header.NAME,
                Header.RIGHT_ASCENSION,
                Header.DECLINATION
        };
        String outputCSVString = converter.convertObjectJournalToCSV(emptyJournal,canonicalHeadersNoCompletion);
        String expectedString = "Name,Right Ascension,Declination\r\n";
        Assertions.assertEquals(expectedString,outputCSVString);
    }

    @Test
    public void testConvertObjectJournalToCSVTwoElementJournalRADecOnlyCanonicalOrder() throws IOException {
        CSVConverter converter = new CSVConverter();
        ObjectJournal twoElementJournal = buildM13M31ObjectJournal();
        Header[] canonicalHeadersNoCompletion = new Header[]{
                Header.NAME,
                Header.RIGHT_ASCENSION,
                Header.DECLINATION
        };
        String outputCSVString = converter.convertObjectJournalToCSV(
                twoElementJournal,
                canonicalHeadersNoCompletion
        );
        // The abundance of double quotes is a feature of CSV. Pairs of double quotes ("") in CSV translate to a single
        // double quote in the cell, and any cell with special characters starts and ends with a single double quote.
        String expectedString = """
                Name,Right Ascension,Declination\r
                M13,16h 41m 41.24s,"+36° 27' 35.5""\"\r
                M31,00h 42m 44.3s,"+41° 16' 09""\"\r
                """;
        Assertions.assertEquals(expectedString,outputCSVString);
    }

    @Test
    public void testConvertObjectJournalToCSVTwoElementJournalRADecOnlyReordered() throws IOException {
        CSVConverter converter = new CSVConverter();
        ObjectJournal twoElementJournal = buildM13M31ObjectJournal();
        Header[] reorderedHeadersNoCompletion = new Header[]{
                Header.NAME,
                Header.DECLINATION,
                Header.RIGHT_ASCENSION
        };
        String outputCSVString = converter.convertObjectJournalToCSV(
                twoElementJournal,
                reorderedHeadersNoCompletion
        );
        // The abundance of double quotes is a feature of CSV. Pairs of double quotes ("") in CSV translate to a single
        // double quote in the cell, and any cell with special characters starts and ends with a single double quote.
        String expectedString = """
                Name,Declination,Right Ascension\r
                M13,"+36° 27' 35.5""\",16h 41m 41.24s\r
                M31,"+41° 16' 09""\",00h 42m 44.3s\r
                """;
        Assertions.assertEquals(expectedString,outputCSVString);
    }

    @Test
    public void testConvertObjectJournalToCSVTwoElementJournalRADecCompletionCanonicalOrder() throws IOException {
        CSVConverter converter = new CSVConverter();
        ObjectJournal twoElementJournal = buildM13M31ObjectJournal();
        Header[] canonicalHeaders = new Header[]{
                Header.NAME,
                Header.RIGHT_ASCENSION,
                Header.DECLINATION,
                Header.COMPLETION_DATE
        };
        String outputCSVString = converter.convertObjectJournalToCSV(twoElementJournal,canonicalHeaders);
        String expectedString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36° 27' 35.5""\",\r
                M31,00h 42m 44.3s,"+41° 16' 09""\",2023-01-01\r
                """;
        Assertions.assertEquals(expectedString,outputCSVString);
    }



    @Test
    public void testBuildObjectJournalFromCSVEmptyJournal()
            throws InvalidJournalFileContentsException, CouldNotParseJournalFileException {
        CSVConverter converter = new CSVConverter();
        ObjectJournal emptyJournal = new ObjectJournal();
        String csvString = "Name,Right Ascension,Declination,Completion Date\r";
        ObjectJournal convertedJournal = converter.buildObjectJournalFromCSV(csvString);
        Assertions.assertEquals(emptyJournal,convertedJournal);
    }

    @Test
    public void testBuildObjectJournalFromCSVTwoElementJournal()
            throws InvalidJournalFileContentsException, CouldNotParseJournalFileException {
        CSVConverter converter = new CSVConverter();
        ObjectJournal twoElementJournal = buildM13M31ObjectJournal();
        String csvString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36° 27' 35.5""\",\r
                M31,00h 42m 44.3s,"+41° 16' 09""\",2023-01-01\r
                """;
        ObjectJournal convertedJournal = converter.buildObjectJournalFromCSV(csvString);
        Assertions.assertEquals(twoElementJournal,convertedJournal);
    }

    @Test
    public void testBuildObjectJournalFromCSVTooFewEntriesThrowsCouldNotParseJournalFileException() {
        CSVConverter converter = new CSVConverter();
        String crashString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36° 27' 35.5""\"\r
                """;
        Assertions.assertThrows(
            CouldNotParseJournalFileException.class,
                () -> converter.buildObjectJournalFromCSV(crashString)
        );
    }

    @Test
    public void testBuildObjectJournalFromCSVWayTooFewEntriesThrowsCouldNotParseJournalFileException() {
        CSVConverter converter = new CSVConverter();
        String crashString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13\r
                """;
        Assertions.assertThrows(
                CouldNotParseJournalFileException.class,
                () -> converter.buildObjectJournalFromCSV(crashString)
        );
    }

    @Test
    public void testBuildObjectJournalFromCSVBadCompletionDateThrowsInvalidJournalContentsException() {
        CSVConverter converter = new CSVConverter();
        String crashString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36° 27' 35.5""\",x\r
                """;
        Assertions.assertThrows(
                InvalidJournalFileContentsException.class,
                () -> converter.buildObjectJournalFromCSV(crashString)
        );
    }

    @Test
    public void testBuildObjectJournalFromCSVBadRACoordinateThrowsInvalidJournalContentsException() {
        CSVConverter converter = new CSVConverter();
        String crashString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41x 41.24s,"+36° 27' 35.5""\",\r
                """;
        Assertions.assertThrows(
                InvalidJournalFileContentsException.class,
                () -> converter.buildObjectJournalFromCSV(crashString)
        );
    }

    @Test
    public void testBuildObjectJournalFromCSVBadDecCoordinateThrowsInvalidJournalContentsException() {
        CSVConverter converter = new CSVConverter();
        String crashString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36u° 27' 35.5""\",\r
                """;
        Assertions.assertThrows(
                InvalidJournalFileContentsException.class,
                () -> converter.buildObjectJournalFromCSV(crashString)
        );
    }

    @Test
    public void testBuildObjectJournalFromCSVInvalidDecCoordinateThrowsInvalidJournalContentsException() {
        CSVConverter converter = new CSVConverter();
        String crashString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+360° 27' 35.5""\",\r
                """;
        Assertions.assertThrows(
                InvalidJournalFileContentsException.class,
                () -> converter.buildObjectJournalFromCSV(crashString)
        );
    }
}
