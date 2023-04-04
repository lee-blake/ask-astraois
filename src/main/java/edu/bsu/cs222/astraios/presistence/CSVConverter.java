package edu.bsu.cs222.astraios.presistence;

import edu.bsu.cs222.astraios.model.astronomy.AstronomicalObject;
import edu.bsu.cs222.astraios.model.astronomy.RightAscensionDeclinationCoordinates;
import edu.bsu.cs222.astraios.model.converters.RightAscensionDeclinationTypeConverter;
import edu.bsu.cs222.astraios.model.exceptions.EntryAlreadyExistsException;
import edu.bsu.cs222.astraios.model.journal.CompletionStatus;
import edu.bsu.cs222.astraios.model.journal.Header;
import edu.bsu.cs222.astraios.model.journal.ObjectJournal;
import edu.bsu.cs222.astraios.model.journal.ObjectJournalEntry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.time.format.DateTimeParseException;

public class CSVConverter {

    public String convertObjectJournalToCSV(ObjectJournal journalToConvert, Header[] headers) throws IOException {
        StringBuilder csvStringBuilder = new StringBuilder();
        String[] headerNames = this.getHeaderNames(headers);
        CSVPrinter printer = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader(headerNames)
                .build()
                .print(csvStringBuilder);
        ObjectJournal.ObjectJournalCSVFormatter formatter = journalToConvert.new ObjectJournalCSVFormatter(printer);
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

    public ObjectJournal buildObjectJournalFromCSV(String csvString)
            throws CouldNotParseJournalFileException, InvalidJournalFileContentsException {
        ObjectJournal parsedJournal = new ObjectJournal();
        StringReader reader = new StringReader(csvString);
        try {
            CSVParser parser = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);
            for (CSVRecord record : parser) {
                ObjectJournalEntry entryFromRecord = this.getEntryFromCSVRecord(record);
                parsedJournal.addEntry(entryFromRecord);
            }
            return parsedJournal;
        }
        catch(UncheckedIOException | IOException csvParsingException) {
            throw new CouldNotParseJournalFileException("Could not parse the journal file - the CSV was not valid!");
        }
        catch(EntryAlreadyExistsException duplicateEntryException) {
            throw new InvalidJournalFileContentsException(
                    "The journal file is invalid because it contains duplicate entries!"
            );
        }
    }

    private ObjectJournalEntry getEntryFromCSVRecord(CSVRecord record)
            throws CouldNotParseJournalFileException, InvalidJournalFileContentsException {
        AstronomicalObject objectFromRecord = this.getAstronomicalObjectFromCSVRecord(record);
        CompletionStatus completionStatus = this.getCompletionStatusFromCSVRecord(record);
        return new ObjectJournalEntry(
                objectFromRecord,
                completionStatus
        );
    }

    private AstronomicalObject getAstronomicalObjectFromCSVRecord(CSVRecord record)
            throws CouldNotParseJournalFileException, InvalidJournalFileContentsException {
        String rightAscension;
        String declination;
        String name;
        try {
            name = record.get(Header.NAME.toString());
            rightAscension = record.get(Header.RIGHT_ASCENSION.toString());
            declination = record.get(Header.DECLINATION.toString());
        } catch (IllegalArgumentException exception) {
            throw new CouldNotParseJournalFileException("A problem occurred when trying to parse the astronomical " +
                    "object from the record '"
                    + record
                    + "'!"
            );
        }
        RightAscensionDeclinationCoordinates coordinates;
        try {
            coordinates = new RightAscensionDeclinationTypeConverter(
                    rightAscension,
                    declination
            ).convert();
        }
        catch(ArrayIndexOutOfBoundsException | IllegalArgumentException exception) {
            throw new InvalidJournalFileContentsException(
                    "One of the coordinates in record '"
                            + record
                            + "' was not valid: "
                            + exception
            );
        }
        return new AstronomicalObject(
                name,
                coordinates
        );
    }

    private CompletionStatus getCompletionStatusFromCSVRecord(CSVRecord record)
            throws CouldNotParseJournalFileException, InvalidJournalFileContentsException {
        String completionDate;
        try {
            completionDate = record.get(Header.COMPLETION_DATE.toString());
        }
        catch (IllegalArgumentException exception) {
            throw new CouldNotParseJournalFileException("A problem occurred when trying to parse completion status " +
                    "from the record '"
                    + record
                    + "'!"
            );
        }
        try {
            return new CSVToCompletionStatusTypeConverter(completionDate)
                    .convert();
        }
        catch(DateTimeParseException exception) {
            throw new InvalidJournalFileContentsException(
                    "Could not parse the completion date '"
                            + completionDate
                            + "' because it is neither empty nor an accepted date format!"
            );
        }
    }
}
