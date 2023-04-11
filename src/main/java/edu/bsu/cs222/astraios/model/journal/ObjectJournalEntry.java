package edu.bsu.cs222.astraios.model.journal;

import edu.bsu.cs222.astraios.model.astronomy.AstronomicalObject;
import edu.bsu.cs222.astraios.model.exceptions.EntryAlreadyCompleteException;
import edu.bsu.cs222.astraios.model.exceptions.EntryAlreadyIncompleteException;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class ObjectJournalEntry {

    private final AstronomicalObject astronomicalObject;
    private CompletionStatus completionStatus;

    public ObjectJournalEntry(AstronomicalObject astronomicalObject) {
        this.astronomicalObject = astronomicalObject;
        this.completionStatus = new CompletionStatus();
    }

    public ObjectJournalEntry(AstronomicalObject astronomicalObject, CompletionStatus completionStatus) {
        this.astronomicalObject = astronomicalObject;
        this.completionStatus = completionStatus;
    }

    public String getName() {
        return this.astronomicalObject.getName();
    }

    public AstronomicalObject getAstronomicalObject() {
        return this.astronomicalObject;
    }

    public void markComplete(LocalDate dateOfCompletion) {
        if(this.completionStatus.isComplete()) {
            throw new EntryAlreadyCompleteException("Cannot mark an entry complete if it is already complete!");
        }
        forceComplete(dateOfCompletion);
    }

    public void forceComplete(LocalDate dateOfCompletion) {
        this.completionStatus = new CompletionStatus(dateOfCompletion);
    }

    public void markIncomplete() {
        if(!this.completionStatus.isComplete()) {
            throw new EntryAlreadyIncompleteException("Cannot mark an entry incomplete if it is already incomplete!");
        }
        this.completionStatus = new CompletionStatus();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObjectJournalEntry other) {
            return this.astronomicalObject.equals(other.astronomicalObject)
                    && this.completionStatus.equals(other.completionStatus);
        }
        return false;
    }



    public class ObjectJournalEntryCSVFormatter {

        private final CSVPrinter csvPrinter;

        public ObjectJournalEntryCSVFormatter(CSVPrinter printer) {
            this.csvPrinter = printer;
        }

        public void printFormattedCSV(Header[] headers) throws IOException {
            Map<Header,String> mainCSVValueMap = this.buildMainCSVValueMap();
            for(Header header: headers) {
                this.csvPrinter.print(mainCSVValueMap.get(header));
            }
            this.csvPrinter.println();
        }

        private Map<Header,String> buildMainCSVValueMap() {
            ObjectJournalEntry parent = ObjectJournalEntry.this;
            AstronomicalObject.AstronomicalObjectCSVFormatter objectFormatter
                    = parent.astronomicalObject.new AstronomicalObjectCSVFormatter();
            Map<Header,String> mainCSVValueMap = objectFormatter.getCSVValueMap();
            CompletionStatus.CompletionStatusCSVFormatter completionFormatter
                    = parent.completionStatus.new CompletionStatusCSVFormatter();
            Map<Header,String> completionCSVValueMap = completionFormatter.getCSVValueMap();
            mainCSVValueMap.putAll(completionCSVValueMap);
            return mainCSVValueMap;
        }
    }



    public class ObjectJournalEntryCLIFormatter {

        public String getCLIViewStringOfEntry() {
            Map<Header,String> mainCLIValueMap = this.buildMainCLIValueMap();
            String name = mainCLIValueMap.get(Header.NAME);
            String rightAscension = mainCLIValueMap.get(Header.RIGHT_ASCENSION);
            String declination = mainCLIValueMap.get(Header.DECLINATION);
            String completion = mainCLIValueMap.get(Header.COMPLETION_DATE);
            return String.format("%-15s   %-11s   %-12s   %-10s\n",
                    name,
                    rightAscension,
                    declination,
                    completion
            );
        }

        private Map<Header,String> buildMainCLIValueMap() {
            ObjectJournalEntry parent = ObjectJournalEntry.this;
            AstronomicalObject.AstronomicalObjectCLIFormatter objectFormatter
                    = parent.astronomicalObject.new AstronomicalObjectCLIFormatter();
            Map<Header,String> mainCLIValueMap = objectFormatter.getCLIValueMap();
            CompletionStatus.CompletionStatusCLIFormatter completionFormatter
                    = parent.completionStatus.new CompletionStatusCLIFormatter();
            Map<Header,String> completionCLIValueMap = completionFormatter.getCLIValueMap();
            mainCLIValueMap.putAll(completionCLIValueMap);
            return mainCLIValueMap;
        }
    }
}
