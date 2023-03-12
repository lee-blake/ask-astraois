package edu.bsu.cs222;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

public class ObjectList {

    private final HashMap<String,ObjectListEntry> nameToEntryMap = new HashMap<>();

    public void addEntry(ObjectListEntry entry) throws EntryAlreadyExistsException {
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

    public ObjectListEntry getEntryByName(String entryName) throws NoSuchEntryException {
        if(!nameToEntryMap.containsKey(entryName)) {
            throw new NoSuchEntryException(
                    "Cannot get entry because no entry has name '"
                    + entryName
                    + "'."
            );
        }
        return nameToEntryMap.get(entryName);
    }

    public void removeEntry(ObjectListEntry entry) throws NoSuchEntryException {
        String entryName = entry.getName();
        removeEntryByName(entryName);
    }

    public void removeEntryByName(String entryName) throws NoSuchEntryException {
        if (!nameToEntryMap.containsKey(entryName)) {
            throw new NoSuchEntryException(
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

    public void markCompleteByName(String name, LocalDate dateOfCompletion)
            throws NoSuchEntryException, EntryAlreadyCompleteException {
        ObjectListEntry entry = this.getEntryByName(name);
        entry.markComplete(dateOfCompletion);
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

        public String getCLIViewString() throws NoSuchEntryException {
            String[] sortedNames = ObjectList.this.nameToEntryMap.keySet().toArray(new String[0]);
            Arrays.sort(sortedNames);
            return this.getCLIViewString(sortedNames);
        }

        public String getCLIViewString(String[] namesOfEntriesToView) throws NoSuchEntryException {
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
            return String.format("%-10s   %-11s   %-12s   %-10s\n",
                    "Name",
                    "R.A.",
                    "Dec.",
                    "Completed"
            );
        }
    }
}
