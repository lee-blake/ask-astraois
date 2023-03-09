package edu.bsu.cs222;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CSVToCompletionStatusTypeConverterTest {

    @Test
    public void testConvertEmptyStringNotComplete() {
        CSVToCompletionStatusTypeConverter converter = new CSVToCompletionStatusTypeConverter("");
        CompletionStatus notComplete = new CompletionStatus();
        CompletionStatus retrieved = converter.convert();
        boolean result = notComplete.equals(retrieved);
        Assertions.assertTrue(result);
    }

    @Test
    public void testConvertFirstOf2023() {
        CSVToCompletionStatusTypeConverter converter = new CSVToCompletionStatusTypeConverter("2023-01-01");
        CompletionStatus firstOf2023 = new CompletionStatus(LocalDate.parse("2023-01-01"));
        CompletionStatus retrieved = converter.convert();
        boolean result = firstOf2023.equals(retrieved);
        Assertions.assertTrue(result);
    }

    @Test
    public void testConvertLastOf2022() {
        CSVToCompletionStatusTypeConverter converter = new CSVToCompletionStatusTypeConverter("2022-12-31");
        CompletionStatus lastOf2022 = new CompletionStatus(LocalDate.parse("2022-12-31"));
        CompletionStatus retrieved = converter.convert();
        boolean result = lastOf2022.equals(retrieved);
        Assertions.assertTrue(result);
    }
}
