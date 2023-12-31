package edu.bsu.cs222.astraios.cli.converters;

import edu.bsu.cs222.astraios.model.astronomy.HourCoordinate;
import edu.bsu.cs222.astraios.model.converters.HourCoordinateTypeConverter;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

import java.util.Locale;

public class CLIHourCoordinateTypeConverter implements ITypeConverter<HourCoordinate> {

    @Override
    public HourCoordinate convert(String value) {
        HourCoordinateTypeConverter converter;
        try {
            String valueLowercase = value.toLowerCase(Locale.ROOT);
            verifyHourCoordinateInSimplestBranch(valueLowercase);
            converter = new HourCoordinateTypeConverter(valueLowercase);
        }
        catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid hour coordinate - it was not in an acceptable form. "
                            + "Accepted forms:\n"
                            + CLIAcceptedFormats.ACCEPTED_HOUR_COORDINATE_FORMATS
            );
        }
        try {
            return converter.convert();
        }
        catch (IllegalArgumentException e) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid hour coordinate.\nAn hour coordinate has integer hours in [0,24) and "
                            + "has integer minutes and real seconds in [0,60)."
            );
        }
    }

    public void verifyHourCoordinateInSimplestBranch(String value) {
        String hourToken = value.split("h")[0];
        int hours = Integer.parseInt(hourToken);
        if(hours < 0 || hours > 23) {
            throw new TypeConversionException("Value '"
                    + value
                    + "' is not a valid hour coordinate.\nAn hour coordinate has integer hours in [0,24) and "
                    + "has integer minutes and real seconds in [0,60)."
            );
        }
    }
}
