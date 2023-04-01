package edu.bsu.cs222.astraios.cli;


import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class CLIOffsetDateTimeTypeConverter implements ITypeConverter<OffsetDateTime> {

    @Override
    public OffsetDateTime convert(String value) {
        try {
            return OffsetDateTime.parse(value);
        }
        catch(DateTimeParseException exception) {
            throw new TypeConversionException("The date and time was not in valid ISO 8601 format!");
        }
    }
}
