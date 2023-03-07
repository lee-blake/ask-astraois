package edu.bsu.cs222;

import java.util.HashMap;

public class ObjectList {

    private final HashMap<String,ObjectListEntry> nameToEntryMap = new HashMap<>();

    public void addEntry(ObjectListEntry entry) throws ObjectListEntryAlreadyExistsException {
        String entryName = entry.getName();
        if(nameToEntryMap.containsKey(entryName)) {
            throw new ObjectListEntryAlreadyExistsException(
                    "Cannot add entry because an entry with name '"
                            + entryName
                            + "' already exists!"
            );
        }
        nameToEntryMap.put(entryName,entry);
    }

    public ObjectListEntry getEntryByName(String entryName) {
        return nameToEntryMap.get(entryName);
    }
}
