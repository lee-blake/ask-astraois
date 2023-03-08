package edu.bsu.cs222;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.Map;

public class ObjectListEntry {

    private final AstronomicalObject astronomicalObject;
    private final CompletionStatus completionStatus;

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
}
