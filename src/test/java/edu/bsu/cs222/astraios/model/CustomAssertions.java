package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;

public class CustomAssertions {

    public static void assertBounded(AngularDistance bound, AngularDistance actual) {
        String messageIfAssertionFails = "Bounded assertion failed: "
                + actual.toString()
                + " was not less than the bound "
                + bound.toString()
                + "!";
        boolean isBounded = actual.lessThan(bound);
        Assertions.assertTrue(isBounded, messageIfAssertionFails);
    }
}
