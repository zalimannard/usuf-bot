package ru.zalimannard;

public abstract class Utils {
    public static boolean isNonNegativeIntegerNumber(String value) {
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
