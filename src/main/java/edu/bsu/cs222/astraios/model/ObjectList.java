package edu.bsu.cs222.astraios.model;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

public class ObjectList {

    private final HashMap<String, ObjectListEntry> nameToEntryMap = new HashMap<>();

    public void addEntry(ObjectListEntry entry) {
        String entryName = entry.getName();
        if(nameToEntryMap.containsKey(entryName)) {
            throw new EntryAlreadyExistsException(
                    "Cannot add entry because an entry with name '"
                            + entryName
                            + "' already exists!"
            );
        }
        nameToEntryMap.put(entryName,entry);
    }

    public ObjectListEntry getEntryByName(String entryName) {
        if(!nameToEntryMap.containsKey(entryName)) {
            throw new NoSuchEntryException(
                    "Cannot get entry because no entry has name '"
                    + entryName
                    + "'."
            );
        }
        return nameToEntryMap.get(entryName);
    }

    public void removeEntry(ObjectListEntry entry) {
        String entryName = entry.getName();
        removeEntryByName(entryName);
    }

    public void removeEntryByName(String entryName) {
        if(!nameToEntryMap.containsKey(entryName)) {
            throw new NoSuchEntryException(
                    "Cannot remove entry because no entry has name '"
                            + entryName
                            + "'."
            );
        }
        nameToEntryMap.remove(entryName);
    }

    public void markCompleteByName(String name, LocalDate dateOfCompletion) {
        ObjectListEntry entry = this.getEntryByName(name);
        entry.markComplete(dateOfCompletion);
    }

    public void markIncompleteByName(String name) {
        ObjectListEntry entry = this.getEntryByName(name);
        entry.markIncomplete();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObjectList other) {
            if(this.nameToEntryMap.size() != other.nameToEntryMap.size()) {
                return false;
            }
            for(String entryName : this.nameToEntryMap.keySet()) {
                ObjectListEntry thisEntry = this.nameToEntryMap.get(entryName);
                // There is no need to check if the key exists in the other's map. If it is not,
                // we'll get null and return false.
                ObjectListEntry otherEntry = other.nameToEntryMap.get(entryName);
                if(!thisEntry.equals(otherEntry)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }



    public class ObjectListCSVFormatter {

        private final CSVPrinter csvPrinter;

        public ObjectListCSVFormatter(CSVPrinter printer) {
            this.csvPrinter = printer;
        }

        public void printFormattedCSV(Header[] headers) throws IOException {
             String[] keyArray = ObjectList.this.nameToEntryMap.keySet().toArray(new String[0]);
             Arrays.sort(keyArray);
             for(String key : keyArray) {
                 ObjectListEntry entry = ObjectList.this.nameToEntryMap.get(key);
                 ObjectListEntry.ObjectListEntryCSVFormatter entryFormatter
                         = entry.new ObjectListEntryCSVFormatter(this.csvPrinter);
                 entryFormatter.printFormattedCSV(headers);
             }
        }
    }



    public class ObjectListCLIFormatter {

        public String getCLIViewString() {
            String[] sortedNames = ObjectList.this.nameToEntryMap.keySet().toArray(new String[0]);
            Arrays.sort(sortedNames);
            return this.getCLIViewString(sortedNames);
        }

        public String getCLIViewString(String[] namesOfEntriesToView) {
            ObjectList parent = ObjectList.this;
            StringBuilder viewStringBuilder = new StringBuilder();
            viewStringBuilder.append(this.buildCLIViewHeaders());
            for(String entryName : namesOfEntriesToView) {
                ObjectListEntry entry = parent.getEntryByName(entryName);
                ObjectListEntry.ObjectListEntryCLIFormatter viewFormatter = entry.new ObjectListEntryCLIFormatter();
                viewStringBuilder.append(viewFormatter.getCLIViewStringOfEntry());
            }
            return viewStringBuilder.toString();
        }

        private String buildCLIViewHeaders() {
            return String.format("%-15s   %-11s   %-12s   %-10s\n",
                    "Name",
                    "R.A.",
                    "Dec.",
                    "Completed"
            );
        }
    }
}
