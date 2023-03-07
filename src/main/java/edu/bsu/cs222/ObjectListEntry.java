package edu.bsu.cs222;

public class ObjectListEntry {

    private final AstronomicalObject astronomicalObject;

    public ObjectListEntry(AstronomicalObject astronomicalObject) {
        this.astronomicalObject = astronomicalObject;
    }

    public String getName() {
        return this.astronomicalObject.getName();
    }
}
