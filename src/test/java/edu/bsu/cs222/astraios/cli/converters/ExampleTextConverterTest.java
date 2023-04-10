package edu.bsu.cs222.astraios.cli.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExampleTextConverterTest {

    @Test
    public void testExampleTextConverterSelfTestJustWorks() {
        ExampleTextConverter converter = new ExampleTextConverter();
        String actual = converter.getExampleStringForSubcommand("example");
        String expected = """
                        Examples for 'example' (which is running right now):
                        
                        Get examples for the example command:
                        ask-astraios example example
                        
                        Get examples for the add command:
                        ask-astraios example add
                        """;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void textExampleTextConverterNonExistentCommandThrowsException() {
        ExampleTextConverter converter = new ExampleTextConverter();
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> converter.getExampleStringForSubcommand("THIS WILL NEVER BE A COMMAND! ALL CAPS!")
        );
    }
}
