package edu.bsu.cs222;

import java.util.HashMap;

public class ObjectList {

    private final HashMap<String,ObjectListEntry> nameToEntryMap = new HashMap<>();

    public void addEntry(ObjectListEntry entry) {
        String entryName = entry.getName();
        nameToEntryMap.put(entryName,entry);
    }

    public ObjectListEntry getEntryByName(String entryName) {
        return nameToEntryMap.get(entryName);
    }
}
