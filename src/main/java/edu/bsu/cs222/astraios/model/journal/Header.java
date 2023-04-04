package edu.bsu.cs222.astraios.model.journal;

public enum Header {

    NAME("Name"),
    RIGHT_ASCENSION("Right Ascension"),
    DECLINATION("Declination"),
    COMPLETION_DATE("Completion Date"),
    ALTITUDE("Altitude"),
    AZIMUTH("Azimuth");

    private final String value;

    Header(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
