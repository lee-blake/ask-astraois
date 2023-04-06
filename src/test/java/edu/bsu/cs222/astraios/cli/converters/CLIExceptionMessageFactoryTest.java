package edu.bsu.cs222.astraios.cli.converters;

import edu.bsu.cs222.astraios.model.exceptions.NoSuchEntryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CLIExceptionMessageFactoryTest {

    @Test
    public void testGetMessageJustWorks() throws Exception {
        CLIExceptionMessageFactory factory = new CLIExceptionMessageFactory();
        String actual = factory.translateExceptionToErrorMessage(new NoSuchEntryException("x"));
        String expected = "One or more requested journal entries could not be retrieved:\nx";
        Assertions.assertEquals(expected, actual);
    }
}
