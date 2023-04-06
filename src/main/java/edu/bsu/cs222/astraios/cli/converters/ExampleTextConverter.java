package edu.bsu.cs222.astraios.cli.converters;

public class ExampleTextConverter {
    public String getExampleStringForSubcommand(String subcommand) {
        if(subcommand.equals("remove")) {
            return """
                Remove M13 from the journal:
                ask-astraios remove M13
                
                Remove an object, spaces in its name:
                ask-astraios remove "has spaces"
                """;
        } else if(subcommand.equals("view")) {
            return """
                    View all objects in journal:
                    ask-astraios view
                    
                    View only objects M13, M31, and M82:
                    ask-astraios view M13 M31 M82
                    
                    View multiple objects if one or more have spaces:
                    ask-astraios view M13 "has spaces" M82
                    """;
        }
        throw new IllegalArgumentException("No such subcommand");
    }
}
