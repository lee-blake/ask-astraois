package edu.bsu.cs222;

import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.TypeConversionException;

public class HalfCircleFractionalDegreeTypeConverter implements ITypeConverter<Double> {

    @Override
    public Double convert(String value) throws ParameterException {
        double doubleValue;
        try {
            doubleValue = Double.parseDouble(value);
        }
        catch (NumberFormatException exception) {
            throw new TypeConversionException(
                    "Half-circle degree coordinate '"
                            + value
                            + "' was not passed in any parsable form! "
                            + "Acceptable forms are: "
                            + "Fractional degree (45.9238553)"
            );
        }
        if(doubleValue < -90 || doubleValue > 90)
            throw new TypeConversionException(
                    "Got value '"
                            + value
                            + "' but half-circle degree coordinates should be in [-90,90]!"
            );
        return doubleValue;
    }
}
