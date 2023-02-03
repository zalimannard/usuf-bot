package ru.zalimannard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    void isNonNegativeIntegerNumber_stringCorrectNumber_trueReturned() {
        String value = "123";

        assertTrue(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_stringFromZero_trueReturned() {
        String value = "0";

        assertTrue(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_stringOfLetters_falseReturned() {
        String value = "A";

        assertFalse(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_stringMixedSymbols_falseReturned() {
        String value = "10A00";

        assertFalse(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_emptyString_falseReturned() {
        String value = "";

        assertFalse(Utils.isNonNegativeIntegerNumber(value));
    }
}