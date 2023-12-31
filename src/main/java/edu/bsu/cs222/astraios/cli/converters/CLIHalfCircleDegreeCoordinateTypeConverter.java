package edu.bsu.cs222.astraios.cli.converters;

import edu.bsu.cs222.astraios.model.converters.DegreeCoordinateTypeConverter;
import edu.bsu.cs222.astraios.model.astronomy.HalfCircleDegreeCoordinate;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

public class CLIHalfCircleDegreeCoordinateTypeConverter implements ITypeConverter<HalfCircleDegreeCoordinate> {

    @Override
    public HalfCircleDegreeCoordinate convert(String value) {
        DegreeCoordinateTypeConverter converter;
        try {
            String standardizedValue = value.replace("*","°");
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
        try {
            return converter.convertHalfCircle();
        }
        catch (IllegalArgumentException invalidValuesException) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid half-circle degree coordinate.\nA half-circle degree coordinate is in "
                            + "the range -90° to +90° and has integer arcminutes\n"
                            + "and real arcseconds in [0,60)."
            );
        }
    }
}
