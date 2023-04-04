package edu.bsu.cs222.astraios.cli.converters;

import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CLILocalDateTypeConverter implements ITypeConverter<LocalDate> {

    @Override
    public LocalDate convert(String value) {
        if(value.equalsIgnoreCase("today")) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(value);
        }
        catch(DateTimeParseException exception) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid date - it was not in an acceptable form. "
                            + "Accepted forms:\n"
                            + CLIAcceptedFormats.ACCEPTED_DATE_FORMATS
            );
        }
    }
}
