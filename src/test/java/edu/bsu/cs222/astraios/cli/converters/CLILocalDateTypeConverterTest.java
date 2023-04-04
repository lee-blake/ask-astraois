package edu.bsu.cs222.astraios.cli.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.TypeConversionException;

import java.time.LocalDate;

public class CLILocalDateTypeConverterTest {

    @Test
    public void testConvertValidDateCovertsCorrectly() {
        String validDate = "2020-01-01";
        CLILocalDateTypeConverter converter = new CLILocalDateTypeConverter();
        LocalDate actual = converter.convert(validDate);
        LocalDate expected = LocalDate.of(
                2020, 1, 1
        );
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertTodayCovertsCorrectly() {
        String validDate = "today";
        CLILocalDateTypeConverter converter = new CLILocalDateTypeConverter();
        LocalDate actual = converter.convert(validDate);
        LocalDate expected = LocalDate.now();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertGarbageThrowsException() {
        String garbage = "nope.avi";
        CLILocalDateTypeConverter converter = new CLILocalDateTypeConverter();
        Assertions.assertThrows(
                TypeConversionException.class,
                () -> converter.convert(garbage)
        );
    }
}
