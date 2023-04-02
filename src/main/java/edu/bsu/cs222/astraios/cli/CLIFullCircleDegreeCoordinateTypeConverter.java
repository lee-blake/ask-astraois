package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.DegreeCoordinateTypeConverter;
import edu.bsu.cs222.astraios.model.FullCircleDegreeCoordinate;
import picocli.CommandLine;

public class CLIFullCircleDegreeCoordinateTypeConverter
        implements CommandLine.ITypeConverter<FullCircleDegreeCoordinate>  {

    @Override
    public FullCircleDegreeCoordinate convert(String value) {
        DegreeCoordinateTypeConverter converter;
        try {
            String standardizedValue = value.replace("*", "°");
            converter = new DegreeCoordinateTypeConverter(standardizedValue);
        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException invalidFormException) {
            throw new CommandLine.TypeConversionException(
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
