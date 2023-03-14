package edu.bsu.cs222.astraios.model;

public enum Header {

    NAME("Name"),
    RIGHT_ASCENSION("Right Ascension"),
    DECLINATION("Declination"),
    COMPLETION_DATE("Completion Date");

    private final String value;

    Header(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
