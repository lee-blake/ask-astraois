package edu.bsu.cs222.astraios.cli.converters;

public class CLIAcceptedFormats {

    public static final String ACCEPTED_DEGREE_COORDINATE_FORMATS = """
            \tStandard degree form (40° 30' 26")
            \tAsterisk degree form (40* 30' 26")""";

    public static final String ACCEPTED_HOUR_COORDINATE_FORMATS = """
            \tStandard hour form (22h 30m 26s)""";

    public static final String ACCEPTED_DATETIME_FORMATS = """
            \tISO 8601 (2023-12-31T23:59:59+01:00)""";

    public static final String ACCEPTED_DATE_FORMATS = """
            \tISO 860-1 (2023-12-31)""";
}
