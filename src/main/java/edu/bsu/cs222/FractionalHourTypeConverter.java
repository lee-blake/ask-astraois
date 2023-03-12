package edu.bsu.cs222;

import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

public class FractionalHourTypeConverter implements ITypeConverter<Double> {

    @Override
    public Double convert(String value) {
        double doubleValue;
        try {
            doubleValue = Double.parseDouble(value);
        }
        catch(NumberFormatException exception) {
            throw new TypeConversionException(
                    "Hour coordinate '"
                            + value
                            + "' was not passed in any parsable form! "
                            + "Acceptable forms are: "
                            + "Fractional hour (5.894231)"
            );
        }
        if(doubleValue < 0 || doubleValue >= 24)
            throw new TypeConversionException(
                    "Got value '"
                            + value
                            + "' but hour coordinates should be in [0,23]!"
            );
        return doubleValue;
    }
}
