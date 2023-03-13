package edu.bsu.cs222;

import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

public class CLIHourCoordinateTypeConverter implements ITypeConverter<HourCoordinate> {

    @Override
    public HourCoordinate convert(String value) {
        HourCoordinateTypeConverter converter;
        try {
            converter = new HourCoordinateTypeConverter(value);
        }
        catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid hour coordinate - it was not in an acceptable form. "
                            + "Accepted forms:\n"
                            + "\tStandard hour form (22h 30m 26s)\n"
            );
        }
        try {
            return converter.convert();
        }
        catch (IllegalArgumentException e) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid hour coordinate.\nAn hour coordinate "
                            + "has integer minutes and real seconds in [0,60)."
            );
        }
    }
}
