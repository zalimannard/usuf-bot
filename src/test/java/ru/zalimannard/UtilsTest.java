package ru.zalimannard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void isNonNegativeIntegerNumber_String123_trueReturned() {
        String value = "123";

        assertTrue(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_String0_trueReturned() {
        String value = "0";

        assertTrue(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_StringA_falseReturned() {
        String value = "A";

        assertFalse(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_String10A00_falseReturned() {
        String value = "10A00";

        assertFalse(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_EmptyString_falseReturned() {
        String value = "";

        assertFalse(Utils.isNonNegativeIntegerNumber(value));
    }

    @Test
    void isNonNegativeIntegerNumber_Null_falseReturned() {
        String value = null;

        assertFalse(Utils.isNonNegativeIntegerNumber(value));
    }
}