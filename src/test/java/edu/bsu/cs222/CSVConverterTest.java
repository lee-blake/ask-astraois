package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

// Suppressing warnings for the Unicode escaping of the degree character in this file because the fix - replacing
// the escape with the literal character - has caused unmappable character errors when running this project
// with default settings. As the inlining of the characters does not impede the functionality, only the readability,
// and as the context indicates it is the degree character, fixing this warning will be delayed until all team members
// can meet and confirm that the change does not cause errors on their respective systems.
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class CSVConverterTest {

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
    public void testConvertObjectListToCSVTwoElementListRADecOnlyCanonicalOrder()
            throws ObjectListEntryAlreadyExistsException, IOException {
        CSVConverter converter = new CSVConverter();
        ObjectList twoElementListNoCompletion = new ObjectList();
        twoElementListNoCompletion.addEntry(new ObjectListEntry(buildM13Object()));
        twoElementListNoCompletion.addEntry(new ObjectListEntry(buildM31Object()));
        Header[] canonicalHeadersNoCompletion = new Header[]{
                Header.NAME,
                Header.RIGHT_ASCENSION,
                Header.DECLINATION
        };
        String outputCSVString = converter.convertObjectListToCSV(
                twoElementListNoCompletion,
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
    public void testConvertObjectListToCSVTwoElementListRADecOnlyReordered()
            throws ObjectListEntryAlreadyExistsException, IOException {
        CSVConverter converter = new CSVConverter();
        ObjectList twoElementListNoCompletion = new ObjectList();
        twoElementListNoCompletion.addEntry(new ObjectListEntry(buildM13Object()));
        twoElementListNoCompletion.addEntry(new ObjectListEntry(buildM31Object()));
        Header[] reorderedHeadersNoCompletion = new Header[]{
                Header.NAME,
                Header.DECLINATION,
                Header.RIGHT_ASCENSION
        };
        String outputCSVString = converter.convertObjectListToCSV(
                twoElementListNoCompletion,
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
    public void testConvertObjectListToCSVTwoElementListRADecCompletionCanonicalOrder()
            throws ObjectListEntryAlreadyExistsException, IOException {
        CSVConverter converter = new CSVConverter();
        ObjectList twoElementListWithCompletion = new ObjectList();
        twoElementListWithCompletion.addEntry(new ObjectListEntry(buildM13Object()));
        twoElementListWithCompletion.addEntry(new ObjectListEntry(
                buildM31Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        Header[] canonicalHeaders = new Header[]{
                Header.NAME,
                Header.RIGHT_ASCENSION,
                Header.DECLINATION,
                Header.COMPLETION_DATE
        };
        String outputCSVString = converter.convertObjectListToCSV(twoElementListWithCompletion,canonicalHeaders);
        String expectedString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36\u00b0 27' 35.5""\",\r
                M31,00h 42m 44.3s,"+41\u00b0 16' 09""\",2023-01-01\r
                """;
        Assertions.assertEquals(expectedString,outputCSVString);
    }



    @Test
    public void testBuildObjectListFromCSVEmptyList() throws IOException, ObjectListEntryAlreadyExistsException {
        CSVConverter converter = new CSVConverter();
        ObjectList emptyList = new ObjectList();
        String csvString = "Name,Right Ascension,Declination,Completion Date\r";
        ObjectList retrievedList = converter.buildObjectListFromCSV(csvString);
        boolean result = emptyList.equals(retrievedList);
        Assertions.assertTrue(result);
    }

    @Test
    public void testBuildObjectListFromCSVTwoElementList() throws ObjectListEntryAlreadyExistsException, IOException {
        CSVConverter converter = new CSVConverter();
        ObjectList twoElementList = new ObjectList();
        twoElementList.addEntry(new ObjectListEntry(buildM13Object()));
        twoElementList.addEntry(new ObjectListEntry(
                buildM31Object(),
                new CompletionStatus(LocalDate.parse("2023-01-01"))
        ));
        String csvString = """
                Name,Right Ascension,Declination,Completion Date\r
                M13,16h 41m 41.24s,"+36\u00b0 27' 35.5""\",\r
                M31,00h 42m 44.3s,"+41\u00b0 16' 09""\",2023-01-01\r
                """;
        ObjectList retrievedList = converter.buildObjectListFromCSV(csvString);
        boolean result = twoElementList.equals(retrievedList);
        Assertions.assertTrue(result);
    }
}
