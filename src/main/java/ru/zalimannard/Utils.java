package ru.zalimannard;

/**
 * The abstract class Utils.
 */
public abstract class Utils {
    /**
     * Checking that the string is an integer number. Leading zeros are discarded.
     *
     * @param value to check for whether it is an integer number
     * @return true if the value is integer and non-negative number
     */
    public static Boolean isNonNegativeIntegerNumber(String value) {
        if (value == null) {
            return false;
        }
        if (value.length() == 0) {
            return false;
        }

        String digits = "0123456789";
        for (int i = 0; i < value.length(); ++i) {
            if (!digits.contains(String.valueOf(value.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}
