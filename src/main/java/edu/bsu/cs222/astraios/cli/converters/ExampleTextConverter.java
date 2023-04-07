package edu.bsu.cs222.astraios.cli.converters;

public class ExampleTextConverter {
    public String getExampleStringForSubcommand(String subcommand) {
        switch (subcommand) {
            case "add" -> {
                return """
                        Add M13 to the object journal:
                        ask-astraios add M13 --ra "16h 41m 41.24s" 	--dec "+36* 27' 35.5\\""
                        
                        Add M83 to the object journal, leaving of second and arcsecond markers:
                        ask-astraios add M83 --ra "13h 37m 00.9" --dec "−29* 51' 57"
                        
                        Add an object with spaces in its name to the journal:
                        ask-astraios add "Name has spaces" --ra "2h 18m 36" --dec "-0* 59' 59.9"
                        """;
            }
            case "check-visibility" -> {
                return """
                        Check the visibility of M13 at noon UTC on Jan 1, 2000 at longitude and latitude 0:
                        ask-astraios check-visibility M13 --datetime "2000-01-01T12:00:00Z"
                        --longitude "0*0'0\\"" --latitude "0*0'0\\""
                        
                        Check the visibility of M31 at 10pm local on Apr 7, 2023 at Ball State University:
                        ask-astraios check-visibility M13 --datetime "2023-04-07T22:00:00-04:00"
                        --longitude "-85°24'32.20\\"" --latitude "40°11'53.96\\""
                        
                        Check the visibility of M31 at BSU again, but with the arcsecond markers left off:
                        ask-astraios check-visibility M13 --datetime "2023-04-07T22:00:00-04:00"
                        --longitude "-85°24'32.20" --latitude "40°11'53.96"
                        
                        Check the visibility of an object with spaces in its name:
                        ask-astraios check-visibility "Name has spaces" --datetime "2023-04-07T22:00:00-04:00"
                        --longitude "-85°24'32.20" --latitude "40°11'53.96"
                        """;
            }
            case "complete" -> {
                return """
                        Mark M13 complete in the journal on today's date:
                        ask-astraios complete M13
                        
                        Mark M13 complete in the journal on the first day of 2023:
                        ask-astraios complete M13 --on "2023-01-01"
                        
                        Mark an object complete if it has spaces in its name:
                        ask-astraios complete "Name has spaces"
                        """;
            }
            case "example" -> {
                return """
                        Get examples for the example command:
                        ask-astraios example example
                        
                        Get examples for the add command:
                        ask-astraios example add
                        """;
            }
            case "remove" -> {
                return """
                        Remove M13 from the journal:
                        ask-astraios remove M13
                                        
                        Remove an object if it has spaces in its name:
                        ask-astraios remove "Name has spaces"
                        """;
            }
            case "uncomplete" -> {
                return """
                        Mark M13 incomplete in the hournal:
                        ask-astraios uncomplete M13
                                        
                        Remove an object if it has spaces in its name:
                        ask-astraios uncomplete "Name has spaces"
                        """;
            }
            case "view" -> {
                return """
                        View all objects in journal:
                        ask-astraios view
                                            
                        View only objects M13, M31, and M82:
                        ask-astraios view M13 M31 M82
                                            
                        View multiple objects if one or more have spaces:
                        ask-astraios view M13 "Name has spaces" M82
                        """;
            }
        }
        throw new IllegalArgumentException("No such subcommand");
    }
}
