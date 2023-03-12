package edu.bsu.cs222;

import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CustomLocalDateTypeConverter implements ITypeConverter<LocalDate> {
    @Override
    public LocalDate convert(String value) {
        if(value.equalsIgnoreCase("today")) {
            return LocalDate.now();
        }
        try {
            return LocalDate.parse(value);
        }
        catch(DateTimeParseException exception) {
            throw new TypeConversionException("Date must either be 'today' or in yyyy-mm-dd format!");
        }
    }
}
