package edu.bsu.cs222;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public class CSVConverter {

    public String convertObjectListToCSV(ObjectList listToConvert, Header[] headers) throws IOException {
        StringBuilder csvStringBuilder = new StringBuilder();
        String[] headerNames = new String[headers.length];
        for(int i = 0; i < headers.length; i++) {
            headerNames[i] = headers[i].toString();
        }
        CSVPrinter printer = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader(headerNames)
                .build()
                .print(csvStringBuilder);
        ObjectList.ObjectListCSVFormatter formatter = listToConvert.new ObjectListCSVFormatter(printer);
        formatter.printFormattedCSV(headers);
        printer.close();
        return csvStringBuilder.toString();
    }
}
