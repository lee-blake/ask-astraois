package edu.bsu.cs222.astraios.model;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

public class ObjectJournal {

    private final HashMap<String, ObjectJournalEntry> nameToEntryMap = new HashMap<>();

    public ObjectJournalEntry getEntryByName(String entryName) {
        if(!nameToEntryMap.containsKey(entryName)) {
            throw new NoSuchEntryException(
                    "Cannot get entry because no entry has name '"
                            + entryName
                            + "'."
            );
        }
        return nameToEntryMap.get(entryName);
    }

    public void addEntry(ObjectJournalEntry entry) {
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

    public void removeEntry(ObjectJournalEntry entry) {
        String entryName = entry.getName();
        removeEntryByName(entryName);
    }

    public void markCompleteByName(String name, LocalDate dateOfCompletion) {
        ObjectJournalEntry entry = this.getEntryByName(name);
        entry.markComplete(dateOfCompletion);
    }

    public void markIncompleteByName(String name) {
        ObjectJournalEntry entry = this.getEntryByName(name);
        entry.markIncomplete();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObjectJournal other) {
            if(this.nameToEntryMap.size() != other.nameToEntryMap.size()) {
                return false;
            }
            for(String entryName : this.nameToEntryMap.keySet()) {
                ObjectJournalEntry thisEntry = this.nameToEntryMap.get(entryName);
                // There is no need to check if the key exists in the other's map. If it is not,
                // we'll get null and return false.
                ObjectJournalEntry otherEntry = other.nameToEntryMap.get(entryName);
                if(!thisEntry.equals(otherEntry)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }



    public class ObjectJournalCSVFormatter {

        private final CSVPrinter csvPrinter;

        public ObjectJournalCSVFormatter(CSVPrinter printer) {
            this.csvPrinter = printer;
        }

        public void printFormattedCSV(Header[] headers) throws IOException {
             String[] keyArray = ObjectJournal.this.nameToEntryMap.keySet().toArray(new String[0]);
             Arrays.sort(keyArray);
             for(String key : keyArray) {
                 ObjectJournalEntry entry = ObjectJournal.this.nameToEntryMap.get(key);
                 ObjectJournalEntry.ObjectJournalEntryCSVFormatter entryFormatter
                         = entry.new ObjectJournalEntryCSVFormatter(this.csvPrinter);
                 entryFormatter.printFormattedCSV(headers);
             }
        }
    }



    public class ObjectJournalCLIFormatter {

        public String getCLIViewString() {
            String[] sortedNames = ObjectJournal.this.nameToEntryMap.keySet().toArray(new String[0]);
            Arrays.sort(sortedNames);
            return this.getCLIViewString(sortedNames);
        }

        public String getCLIViewString(String[] namesOfEntriesToView) {
            ObjectJournal parent = ObjectJournal.this;
            StringBuilder viewStringBuilder = new StringBuilder();
            viewStringBuilder.append(this.buildCLIViewHeaders());
            for(String entryName : namesOfEntriesToView) {
                ObjectJournalEntry entry = parent.getEntryByName(entryName);
                ObjectJournalEntry.ObjectJournalEntryCLIFormatter cliFormatter =
                        entry.new ObjectJournalEntryCLIFormatter();
                viewStringBuilder.append(cliFormatter.getCLIViewStringOfEntry());
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
