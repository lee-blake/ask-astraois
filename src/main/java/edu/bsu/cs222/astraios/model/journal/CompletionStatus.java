package edu.bsu.cs222.astraios.model.journal;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CompletionStatus {

    private final boolean complete;
    private LocalDate dateOfCompletion;

    public CompletionStatus() {
        this.complete = false;
    }

    public CompletionStatus(LocalDate dateOfCompletion) {
        this.complete = true;
        this.dateOfCompletion = dateOfCompletion;
    }

    public boolean isComplete() {
        return CompletionStatus.this.complete;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof CompletionStatus other) {
            if(this.complete && other.complete) {
                return this.dateOfCompletion.equals(other.dateOfCompletion);
            }
            else {
                return this.complete == other.complete;
            }
        }
        return false;
    }



    public class CompletionStatusCSVFormatter {

        public Map<Header, String> getCSVValueMap() {
            HashMap<Header, String> csvValueMap = new HashMap<>();
            // No completion = empty cell. This is how most users store N/A in CSV.
            String completionDateString = "";
            if(CompletionStatus.this.complete) {
                completionDateString = CompletionStatus.this.dateOfCompletion.toString();
            }
            csvValueMap.put(Header.COMPLETION_DATE,completionDateString);
            return csvValueMap;
        }
    }



    public class CompletionStatusCLIFormatter {

        public Map<Header, String> getCLIValueMap() {
            HashMap<Header, String> cliValueMap = new HashMap<>();
            // No completion = don't print anything. This will cause less visual clutter than printing
            // something incomplete.
            String completionDateString = "";
            if(CompletionStatus.this.complete) {
                completionDateString = CompletionStatus.this.dateOfCompletion.toString();
            }
            cliValueMap.put(Header.COMPLETION_DATE, completionDateString);
            return cliValueMap;
        }
    }
}
