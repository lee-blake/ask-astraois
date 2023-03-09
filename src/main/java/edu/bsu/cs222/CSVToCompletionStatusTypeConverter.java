package edu.bsu.cs222;

import java.time.LocalDate;

public class CSVToCompletionStatusTypeConverter {

    private final String dateString;

    public CSVToCompletionStatusTypeConverter(String csvString) {
        this.dateString = csvString;
    }

    public CompletionStatus convert() {
        // Remember that the typical way to store a N/A is an empty cell, which parses as an empty string
        if(this.dateString.equals("")) {
            return new CompletionStatus();
        }
        else {
            return new CompletionStatus(LocalDate.parse(this.dateString));
        }
    }
}
