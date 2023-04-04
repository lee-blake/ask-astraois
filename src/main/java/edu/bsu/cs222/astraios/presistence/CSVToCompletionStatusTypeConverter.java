package edu.bsu.cs222.astraios.presistence;

import edu.bsu.cs222.astraios.model.journal.CompletionStatus;

import java.time.LocalDate;

public class CSVToCompletionStatusTypeConverter {

    private final String dateString;

    public CSVToCompletionStatusTypeConverter(String csvString) {
        this.dateString = csvString;
    }

    public CompletionStatus convert() {
        // Remember that the typical way to store a N/A is an empty cell, which parses as an empty string.
        // This is due to the implementation of Commons CSV, and we need to support it.
        if(this.dateString.equals("")) {
            return new CompletionStatus();
        }
        else {
            return new CompletionStatus(LocalDate.parse(this.dateString));
        }
    }
}
