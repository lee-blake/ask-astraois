package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CSVToCompletionStatusTypeConverterTest {

    @Test
    public void testConvertEmptyStringNotComplete() {
        CSVToCompletionStatusTypeConverter converter = new CSVToCompletionStatusTypeConverter("");
        CompletionStatus notComplete = new CompletionStatus();
        CompletionStatus actual = converter.convert();
        Assertions.assertEquals(notComplete,actual);
    }

    @Test
    public void testConvertFirstOf2023() {
        CSVToCompletionStatusTypeConverter converter = new CSVToCompletionStatusTypeConverter("2023-01-01");
        CompletionStatus firstOf2023 = new CompletionStatus(LocalDate.parse("2023-01-01"));
        CompletionStatus actual = converter.convert();
        Assertions.assertEquals(firstOf2023,actual);
    }

    @Test
    public void testConvertLastOf2022() {
        CSVToCompletionStatusTypeConverter converter = new CSVToCompletionStatusTypeConverter("2022-12-31");
        CompletionStatus lastOf2022 = new CompletionStatus(LocalDate.parse("2022-12-31"));
        CompletionStatus actual = converter.convert();
        Assertions.assertEquals(lastOf2022,actual);
    }
}
