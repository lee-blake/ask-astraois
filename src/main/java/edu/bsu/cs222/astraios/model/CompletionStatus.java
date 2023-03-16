package edu.bsu.cs222.astraios.model;

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



    public class CompletionStatusCLIViewFormatter {

        public Map<Header, String> getCLIViewValueMap() {
            HashMap<Header, String> cliViewValueMap = new HashMap<>();
            // No completion = don't print everything. This is the current display of the view command.
            String completionDateString = "";
            if(CompletionStatus.this.complete) {
                completionDateString = CompletionStatus.this.dateOfCompletion.toString();
            }
            cliViewValueMap.put(Header.COMPLETION_DATE, completionDateString);
            return cliViewValueMap;
        }
    }
}
