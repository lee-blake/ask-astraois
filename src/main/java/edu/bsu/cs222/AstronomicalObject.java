package edu.bsu.cs222;

public class AstronomicalObject {


    private final String name;
    private final RightAscensionDeclinationCoordinates raDecCoords;

    public AstronomicalObject(String name, RightAscensionDeclinationCoordinates raDecCoords) {
        this.name = name;
        this.raDecCoords = raDecCoords;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AstronomicalObject other) {
            return this.name.equals(other.name) && this.raDecCoords.equals(other.raDecCoords);
        }
        return false;
    }

    public String getName() {
        return this.name;
    }
}
