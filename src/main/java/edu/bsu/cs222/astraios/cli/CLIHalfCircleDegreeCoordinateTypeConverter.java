package edu.bsu.cs222.astraios.cli;

import edu.bsu.cs222.astraios.model.DegreeCoordinateTypeConverter;
import edu.bsu.cs222.astraios.model.HalfCircleDegreeCoordinate;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

// Suppressing warnings for the Unicode escaping of the degree character in this file because the fix - replacing
// the escape with the literal character - has caused unmappable character errors when running this project
// with default settings. As the inlining of the characters does not impede the functionality, only the readability,
// and as the context indicates it is the degree character, fixing this warning will be delayed until all team members
// can meet and confirm that the change does not cause errors on their respective systems.
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class CLIHalfCircleDegreeCoordinateTypeConverter implements ITypeConverter<HalfCircleDegreeCoordinate> {

    @Override
    public HalfCircleDegreeCoordinate convert(String value) throws TypeConversionException {

        DegreeCoordinateTypeConverter converter;
        try {
            String standardizedValue = value.replace("*","\u00b0");
            converter = new DegreeCoordinateTypeConverter(standardizedValue);

        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid degree coordinate - it was not in an acceptable form. "
                            + "Accepted forms:\n"
                            + "\tStandard degree form (40\u00b0 30' 26\")\n"
                            + "\tAsterisk degree form (40* 30' 26\")\n"
            );
        }
        try {
            return converter.convertHalfCircle();
        }
        catch (IllegalArgumentException e) {
            throw new TypeConversionException(
                    "Value '"
                            + value
                            + "' is not a valid half-circle degree coordinate.\nA half-circle degree coordinate is in "
                            + "the range -90\u00b0 to +90\u00b0 and has integer arcminutes\n"
                            + "and real arcseconds in [0,60)."
            );
        }
    }
}
