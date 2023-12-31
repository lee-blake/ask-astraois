package edu.bsu.cs222.astraios.model.converters;

import edu.bsu.cs222.astraios.model.astronomy.RightAscensionDeclinationCoordinates;

public class RightAscensionDeclinationTypeConverter {

    private final String rightAscension;
    private final String declination;

    public RightAscensionDeclinationTypeConverter(String rightAscension, String declination) {
        this.rightAscension = rightAscension;
        this.declination = declination;
    }

    public RightAscensionDeclinationCoordinates convert() {
        return new RightAscensionDeclinationCoordinates(
                new HourCoordinateTypeConverter(this.rightAscension).convert(),
                new DegreeCoordinateTypeConverter(this.declination).convertHalfCircle()
        );
    }
}
