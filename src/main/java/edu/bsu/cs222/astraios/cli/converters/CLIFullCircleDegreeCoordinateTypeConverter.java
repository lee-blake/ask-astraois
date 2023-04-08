package edu.bsu.cs222.astraios.cli.converters;

import edu.bsu.cs222.astraios.model.converters.DegreeCoordinateTypeConverter;
import edu.bsu.cs222.astraios.model.astronomy.FullCircleDegreeCoordinate;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

public class CLIFullCircleDegreeCoordinateTypeConverter
        implements ITypeConverter<FullCircleDegreeCoordinate>  {

    @Override
    public FullCircleDegreeCoordinate convert(String value) {
        DegreeCoordinateTypeConverter converter;
        try {
            String standardizedValue = value.replace("*", "°");
            converter = new DegreeCoordinateTypeConverter(standardizedValue);
        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException invalidFormException) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid degree coordinate - it was not in an acceptable form. "
                            + "Accepted forms:\n"
                            + CLIAcceptedFormats.ACCEPTED_DEGREE_COORDINATE_FORMATS
            );
        }
        return converter.convertFullCircle();
    }
}
