package edu.bsu.cs222;

import javax.naming.NameNotFoundException;
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

    public ObjectListEntry getEntryByName(String entryName) throws NameNotFoundException {
        if(!nameToEntryMap.containsKey(entryName)) {
            throw new NameNotFoundException(
                    "Cannot get entry because no entry has name '"
                    + entryName
                    + "'."
            );
        }
        return nameToEntryMap.get(entryName);
    }

    public void removeEntry(ObjectListEntry entry) throws NameNotFoundException {
        String entryName = entry.getName();
        if (!nameToEntryMap.containsKey(entryName)) {
            throw new NameNotFoundException(
                    "Cannot remove entry because no entry has name '"
                            + entryName
                            + "'."
            );
        }
        nameToEntryMap.remove(entryName);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ObjectList other) {
            if(this.nameToEntryMap.size() != other.nameToEntryMap.size()) {
                return false;
            }
            for(String entryName : this.nameToEntryMap.keySet()) {
                ObjectListEntry thisEntry = this.nameToEntryMap.get(entryName);
                // No need to check if they key exists in the other's map - if not, we'll get null and return false
                ObjectListEntry otherEntry = other.nameToEntryMap.get(entryName);
                if(!thisEntry.equals(otherEntry)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}