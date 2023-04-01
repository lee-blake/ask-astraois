package edu.bsu.cs222.astraios.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.TypeConversionException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class CLIOffsetDateTimeTypeConverterTest {

    // Suppressing this warning because it says the construction of "expected" always fails when opening the test
    // in the debugger clearly demonstrates it constructs correctly. While we could use OffsetDateTime.parse here,
    // it is better to construct with an alternate method to avoid a test that is basically A=A.
    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testConvertValidUTCConvertsCorrectly() {
        String validUTC = "2023-12-31T23:59:59Z";
        CLIOffsetDateTimeTypeConverter converter = new CLIOffsetDateTimeTypeConverter();
        OffsetDateTime actual = converter.convert(validUTC);
        OffsetDateTime expected = OffsetDateTime.of(
                2023,12,31,
                23,59,59,
                0,
                ZoneOffset.UTC
        );
        Assertions.assertEquals(expected,actual);
    }

    // Suppressing this warning because it says the construction of "expected" always fails when opening the test
    // in the debugger clearly demonstrates it constructs correctly. While we could use OffsetDateTime.parse here,
    // it is better to construct with an alternate method to avoid a test that is basically A=A.
    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testConvertValidOffsetConvertsCorrectly() {
        String validOffset = "2023-12-31T23:59:59+01:00";
        CLIOffsetDateTimeTypeConverter converter = new CLIOffsetDateTimeTypeConverter();
        OffsetDateTime actual = converter.convert(validOffset);
        OffsetDateTime expected = OffsetDateTime.of(
                2023,12,31,
                23,59,59,
                0,
                ZoneOffset.UTC
        ).withOffsetSameLocal(ZoneOffset.ofHours(1));
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testConvertInvalidDateThrowsException() {
        String invalidDate = "2023-23-31T00:00:00Z";
        CLIOffsetDateTimeTypeConverter converter = new CLIOffsetDateTimeTypeConverter();
        Assertions.assertThrows(
                TypeConversionException.class,
                () -> converter.convert(invalidDate)
        );
    }

    @Test
    public void testConvertGarbageInputThrowsException() {
        String invalidDate = "not a date";
        CLIOffsetDateTimeTypeConverter converter = new CLIOffsetDateTimeTypeConverter();
        Assertions.assertThrows(
                TypeConversionException.class,
                () -> converter.convert(invalidDate)
        );
    }
}
