package edu.bsu.cs222;

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
}
