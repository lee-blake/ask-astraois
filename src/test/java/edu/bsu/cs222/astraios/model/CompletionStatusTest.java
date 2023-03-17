package edu.bsu.cs222.astraios.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CompletionStatusTest {

    // This suppression is needed to verify this intended functionality where anything of another
    // type is not equal.
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEqualsNonCompletionStatusNotEqual() {
        CompletionStatus status = new CompletionStatus();
        String otherObject = "";
        boolean result = status.equals(otherObject);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsBothIncompleteIsEqual() {
        CompletionStatus status1 = new CompletionStatus();
        CompletionStatus status2 = new CompletionStatus();
        boolean result = status1.equals(status2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsOneIncompleteNotEqual() {
        CompletionStatus status1 = new CompletionStatus();
        CompletionStatus status2 = new CompletionStatus(LocalDate.parse("2023-01-01"));
        boolean result = status1.equals(status2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsDifferentDatesNotEqual() {
        CompletionStatus status1 = new CompletionStatus(LocalDate.parse("2023-01-01"));
        CompletionStatus status2 = new CompletionStatus(LocalDate.parse("2022-12-31"));
        boolean result = status1.equals(status2);
        Assertions.assertFalse(result);
    }

    @Test
    public void testEqualsSameDateIsEqual() {
        CompletionStatus status1 = new CompletionStatus(LocalDate.parse("2023-01-01"));
        CompletionStatus status2 = new CompletionStatus(LocalDate.parse("2023-01-01"));
        boolean result = status1.equals(status2);
        Assertions.assertTrue(result);
    }



    @Test
    public void testEqualsIsCompleteConstructedWithDate() {
        CompletionStatus status = new CompletionStatus(LocalDate.parse("2023-01-01"));
        boolean result = status.isComplete();
        Assertions.assertTrue(result);
    }

    @Test
    public void testEqualsIsCompleteEmptyConstructor() {
        CompletionStatus status = new CompletionStatus();
        boolean result = status.isComplete();
        Assertions.assertFalse(result);
    }
}
