package edu.bsu.cs222.astraios.cli.converters;

import edu.bsu.cs222.astraios.model.exceptions.NoSuchEntryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CLIExceptionMessageFactoryTest {

    @Test
    public void testGetMessageRecognizedExceptionBuildsCorrectMessage() throws Exception {
        CLIExceptionMessageFactory factory = new CLIExceptionMessageFactory();
        String actual = factory.translateExceptionToErrorMessage(new NoSuchEntryException("x"));
        String expected = "One or more requested journal entries could not be retrieved:\nx";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetMessageThrowsExceptionWhenNonRecognizedException() {
        CLIExceptionMessageFactory factory = new CLIExceptionMessageFactory();
        // IllegalThreadStateException is sufficiently obscure for testing - this program is single-threaded and
        // therefore would require an entire restructure for this test to become invalid.
        Assertions.assertThrows(
                IllegalThreadStateException.class,
                () -> factory.translateExceptionToErrorMessage(new IllegalThreadStateException())
        );
    }
}
