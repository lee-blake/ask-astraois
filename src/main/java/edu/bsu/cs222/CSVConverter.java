package edu.bsu.cs222;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;

public class CSVConverter {

    public String convertObjectListToCSV(ObjectList listToConvert, Header[] headers) throws IOException {
        StringBuilder csvStringBuilder = new StringBuilder();
        String[] headerNames = this.getHeaderNames(headers);
        CSVPrinter printer = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader(headerNames)
                .build()
                .print(csvStringBuilder);
        ObjectList.ObjectListCSVFormatter formatter = listToConvert.new ObjectListCSVFormatter(printer);
        formatter.printFormattedCSV(headers);
        printer.close();
        return csvStringBuilder.toString();
    }

    private String[] getHeaderNames(Header[] headers) {
        String[] headerNames = new String[headers.length];
        for(int i = 0; i < headers.length; i++) {
            headerNames[i] = headers[i].toString();
        }
        return headerNames;
    }

    // Suppressing warnings for the Unicode escaping of the degree character in this file because the fix -
    // replacing the escape with the literal character - has caused unmappable character errors when running this
    // project with default settings. As the inlining of the characters does not impede the functionality, only the
    // readability, and as the context indicates it is the degree character, fixing this warning will be delayed
    // until all team members can meet and confirm that the change does not cause errors on their
    // respective systems.
    @SuppressWarnings("UnnecessaryUnicodeEscape")
    public ObjectList buildObjectListFromCSV(String csvString) throws IOException, ObjectListEntryAlreadyExistsException {
        ObjectList parsedList = new ObjectList();
        StringReader reader = new StringReader(csvString);
        CSVParser parser = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);
        for(CSVRecord record: parser) {
            ObjectListEntry entryFromRecord = this.getEntryFromCSVRecord(record);
            parsedList.addEntry(entryFromRecord);
        }
        return parsedList;
    }

    private ObjectListEntry getEntryFromCSVRecord(CSVRecord record) {
        AstronomicalObject objectFromRecord = this.getAstronomicalObjectFromCSVRecord(record);
        CompletionStatus completionStatus = this.getCompletionStatusFromCSVRecord(record);
        return new ObjectListEntry(
                objectFromRecord,
                completionStatus
        );
    }

    private AstronomicalObject getAstronomicalObjectFromCSVRecord(CSVRecord record) {
        String name = record.get(Header.NAME.toString());
        String rightAscension = record.get(Header.RIGHT_ASCENSION.toString());
        String declination = record.get(Header.DECLINATION.toString());
        RightAscensionDeclinationCoordinates raDec = new RightAscensionDeclinationTypeConverter(
                rightAscension,
                declination
        )
                .convert();
        return new AstronomicalObject(
                name,
                raDec
        );
    }

    private CompletionStatus getCompletionStatusFromCSVRecord(CSVRecord record) {
        String completionDate = record.get(Header.COMPLETION_DATE.toString());
        return new CSVToCompletionStatusTypeConverter(completionDate)
                        .convert();
    }
}