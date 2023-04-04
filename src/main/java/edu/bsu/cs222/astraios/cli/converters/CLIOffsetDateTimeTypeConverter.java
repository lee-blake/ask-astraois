package edu.bsu.cs222.astraios.cli.converters;


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
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid date & time- it was not in an acceptable form. "
                            + "Accepted forms:\n"
                            + CLIAcceptedFormats.ACCEPTED_DATETIME_FORMATS
            );
        }
    }
}
