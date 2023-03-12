package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static edu.bsu.cs222.TestObjectFactory.ObjectLists.buildM13M31ObjectList;

// Suppressing warnings for the Unicode escaping of the degree character in this file because the fix - replacing
// the escape with the literal character - has caused unmappable character errors when running this project
// with default settings. As the inlining of the characters does not impede the functionality, only the readability,
// and as the context indicates it is the degree character, fixing this warning will be delayed until all team members
// can meet and confirm that the change does not cause errors on their respective systems.
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class CSVConverterTest {

    @Test
    public void testConvertObjectListToCSVEmptyListCanonicalOrder() throws IOException {
        CSVConverter converter = new CSVConverter();
        ObjectList emptyList = new ObjectList();
        Header[] canonicalHeadersNoCompletion = new Header[]{
                Header.NAME,
                Header.RIGHT_ASCENSION,
                Header.DECLINATION
        };
        String outputCSVString = converter.convertObjectListToCSV(emptyList,canonicalHeadersNoCompletion);
        String expectedString = "Name,Right Ascension,Declination\r\n";
        Assertions.assertEquals(expectedString,outputCSVString);
    }

    @Test
    public void testConvertObjectListToCSVTwoElementListRADecOnlyCanonicalOrder() throws IOException {
        CSVConverter converter = new CSVConverter();
        ObjectList twoElementList = buildM13M31ObjectList();
        Header[] canonicalHeadersNoCompletion = new Header[]{
                Header.NAME,
                Header.RIGHT_ASCENSION,
                Header.DECLINATION
        };
        String outputCSVString = converter.convertObjectListToCSV(
                twoElementList,
                canonicalHeadersNoCompletion
        );
        // The abundance of double quotes is a feature of CSV. Pairs of double quotes ("") in CSV translate to a single
        // double quote in the cell, and any cell with special characters starts and ends with a single double quote.
        String expectedString = """
                Name,Right Ascension,Declination\r
                M13,16h 41m 41.24s,"+36\u00b0 27' 35.5""\"\r
                M31,00h 42m 44.3s,"+41\u00b0 16' 09""\"\r
                """;
        Assertions.assertEquals(expectedString,outputCSVString);
    }

    @Test
    public void testConvertObjectListToCSVTwoElementListRADecOnlyReordered() throws IOException {
        CSVConverter converter = new CSVConverter();
        ObjectList twoElementList = buildM13M31ObjectList();
        Header[] reorderedHeadersNoCompletion = new Header[]{
                Header.NAME,
                Header.DECLINATION,
                Header.RIGHT_ASCENSION
        };
        String outputCSVString = converter.convertObjectListToCSV(
                twoElementList,
                reorderedHeadersNoCompletion
        );
        // The abundance of double quotes is a feature of CSV. Pairs of double quotes ("") in CSV translate to a single
        // double quote in the cell, and any cell with special characters starts and ends with a single double quote.
        String expectedString = """
                Name,Declination,Right Ascension\r
                M13,"+36\u00b0 27' 35.5""\",16h 41m 41.24s\r
                M31,"+41\u00b0 16' 09""\",00h 42m 44.3s\r
                """;
        Assertions.assertEquals(expectedString,outputCSVString);
    }

    @Test
    public void testConvertObjectListToCSVTwoElementListRADecCompletionCanonicalOrder() throws IOException {
        CSVConverter converter = new CSVConverter();
        ObjectList twoElementList = buildM13M31ObjectList();
        Header[] canonicalHeaders = new Header[]{
                Header.NAME,
                Header.RIGHT_ASCENSION,
                Header.DECLINATION,
                Header.COMPLETION_DATE
        };
        String outputCSVString = converter.convertObjectListToCSV(twoElementList,canonicalHeaders);
        String expectedString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36\u00b0 27' 35.5""\",\r
                M31,00h 42m 44.3s,"+41\u00b0 16' 09""\",2023-01-01\r
                """;
        Assertions.assertEquals(expectedString,outputCSVString);
    }



    @Test
    public void testBuildObjectListFromCSVEmptyList()
            throws InvalidJournalFileContentsException, CouldNotParseJournalFileException {
        CSVConverter converter = new CSVConverter();
        ObjectList emptyList = new ObjectList();
        String csvString = "Name,Right Ascension,Declination,Completion Date\r";
        ObjectList convertedList = converter.buildObjectListFromCSV(csvString);
        Assertions.assertEquals(emptyList,convertedList);
    }

    @Test
    public void testBuildObjectListFromCSVTwoElementList()
            throws InvalidJournalFileContentsException, CouldNotParseJournalFileException {
        CSVConverter converter = new CSVConverter();
        ObjectList twoElementList = buildM13M31ObjectList();
        String csvString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36\u00b0 27' 35.5""\",\r
                M31,00h 42m 44.3s,"+41\u00b0 16' 09""\",2023-01-01\r
                """;
        ObjectList convertedList = converter.buildObjectListFromCSV(csvString);
        Assertions.assertEquals(twoElementList,convertedList);
    }
}
