package edu.bsu.cs222;

import java.time.LocalDate;

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

    @Override
    public boolean equals(Object o) {
        if(o instanceof CompletionStatus other) {
            if(this.complete && other.complete){
                return this.dateOfCompletion.equals(other.dateOfCompletion);
            }
            else {
                return this.complete == other.complete;
            }
        }
        return false;
    }
}
