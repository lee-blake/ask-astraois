package edu.bsu.cs222;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class ObjectListEntry {

    private final AstronomicalObject astronomicalObject;
    private CompletionStatus completionStatus;

    public ObjectListEntry(AstronomicalObject astronomicalObject) {
        this.astronomicalObject = astronomicalObject;
        this.completionStatus = new CompletionStatus();
    }

    public ObjectListEntry(AstronomicalObject astronomicalObject, CompletionStatus completionStatus) {
        this.astronomicalObject = astronomicalObject;
        this.completionStatus = completionStatus;
    }

    public String getName() {
        return this.astronomicalObject.getName();
    }

    public AstronomicalObject getAstronomicalObject() {
        return this.astronomicalObject;
    }

    public void markComplete(LocalDate dateOfCompletion) throws EntryAlreadyCompleteException {
        if(this.completionStatus.isComplete()) {
            throw new EntryAlreadyCompleteException("Cannot mark an entry complete if it is already complete!");
        }
        this.completionStatus = new CompletionStatus(dateOfCompletion);
    }

    public void markIncomplete() throws EntryAlreadyIncompleteException {
        if(!this.completionStatus.isComplete()) {
            throw new EntryAlreadyIncompleteException("Cannot mark an entry incomplete if it is already incomplete!");
        }
        this.completionStatus = new CompletionStatus();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObjectListEntry other) {
            return this.astronomicalObject.equals(other.astronomicalObject)
                    && this.completionStatus.equals(other.completionStatus);
        }
        return false;
    }



    public class ObjectListEntryCSVFormatter {

        private final CSVPrinter csvPrinter;

        public ObjectListEntryCSVFormatter(CSVPrinter printer) {
            this.csvPrinter = printer;
        }

        public void printFormattedCSV(Header[] headers) throws IOException {
            ObjectListEntry parent = ObjectListEntry.this;
            AstronomicalObject.AstronomicalObjectCSVFormatter objectFormatter
                    = parent.astronomicalObject.new AstronomicalObjectCSVFormatter();
            Map<Header,String> mainCSVValueMap = objectFormatter.getCSVValueMap();
            CompletionStatus.CompletionStatusCSVFormatter completionFormatter
                    = parent.completionStatus.new CompletionStatusCSVFormatter();
            Map<Header,String> completionCSVValueMap = completionFormatter.getCSVValueMap();
            mainCSVValueMap.putAll(completionCSVValueMap);
            for(Header header: headers) {
                this.csvPrinter.print(mainCSVValueMap.get(header));
            }
            this.csvPrinter.println();
        }
    }



    public class ObjectListEntryCLIFormatter {

        public String getCLIViewStringOfEntry() {
            ObjectListEntry parent = ObjectListEntry.this;
            AstronomicalObject.AstronomicalObjectCLIViewFormatter objectFormatter
                    = parent.astronomicalObject.new AstronomicalObjectCLIViewFormatter();
            Map<Header,String> mainCLIViewValueMap = objectFormatter.getCLIViewValueMap();
            CompletionStatus.CompletionStatusCLIViewFormatter completionFormatter
                    = parent.completionStatus.new CompletionStatusCLIViewFormatter();
            Map<Header,String> completionCLIViewValueMap = completionFormatter.getCLIViewValueMap();
            mainCLIViewValueMap.putAll(completionCLIViewValueMap);
            String name = mainCLIViewValueMap.get(Header.NAME);
            String rightAscension = mainCLIViewValueMap.get(Header.RIGHT_ASCENSION);
            String declination = mainCLIViewValueMap.get(Header.DECLINATION);
            String completion = mainCLIViewValueMap.get(Header.COMPLETION_DATE);
            return String.format("%-15s   %-11s   %-12s   %-10s\n",
                    name,
                    rightAscension,
                    declination,
                    completion
            );
        }
    }
}
