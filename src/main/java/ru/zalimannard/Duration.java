package ru.zalimannard;

import java.util.Objects;

public class Duration {
    private long milliseconds;

    public Duration() {
        milliseconds = 0;
    }

    public Duration(long milliseconds) {
        this.milliseconds = Math.max(0, milliseconds);
    }

    public Duration(String durationAsString) throws IllegalArgumentException {
        try {
            milliseconds = durationAsHmsStringToMilliseconds(durationAsString);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static boolean isCorrectDuration(String hmsString) {
        if (!isCorrectDurationStructure(hmsString)) {
            return false;
        }

        String[] hmsStringParts = hmsString.split(":");
        String seconds = hmsStringParts[hmsStringParts.length - 1];
        if (((hmsStringParts.length > 1) && (seconds.length() != 2)) ||
                (Long.parseLong(seconds) >= 60)) {
            return false;
        }
        if (hmsStringParts.length >= 2) {
            String minutes = hmsStringParts[hmsStringParts.length - 2];
            if (((hmsStringParts.length > 2) && (minutes.length() != 2)) ||
                    (Long.parseLong(minutes) >= 60)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isCorrectDurationStructure(String hms) {
        String[] hmsParts = hms.split(":");
        if ((hmsParts.length < 1) || (hmsParts.length > 3)) {
            return false;
        }
        return arePartsNonNegativeAndIntegers(hmsParts);
    }

    private static boolean arePartsNonNegativeAndIntegers(String[] hmsParts) {
        for (String part : hmsParts) {
            if (!Utils.isNonNegativeIntegerNumber(part)) {
                return false;
            }
        }
        return true;
    }

    private long durationAsHmsStringToMilliseconds(String hmsAsString) throws IllegalArgumentException {
        if (!isCorrectDuration(hmsAsString)) {
            throw new IllegalArgumentException("Duration is not correct: " + hmsAsString);
        }

        String[] hmsParts = hmsAsString.split(":");
        long millisecondsInHms = 0;
        millisecondsInHms += Long.parseLong(hmsParts[hmsParts.length - 1]) * 1000;
        if (hmsParts.length >= 2) {
            millisecondsInHms += Long.parseLong(hmsParts[hmsParts.length - 2]) * 60 * 1000;
        }
        if (hmsParts.length == 3) {
            millisecondsInHms += Long.parseLong(hmsParts[hmsParts.length - 3]) * 60 * 60 * 1000;
        }

        return millisecondsInHms;
    }

    public void add(Duration other) {
        milliseconds += other.getMilliseconds();
    }

    public void sub(Duration other) {
        milliseconds -= other.milliseconds;
        milliseconds = Math.max(0, milliseconds);
    }

    public String getHmsFormat() {
        long totalSeconds = milliseconds / 1000;
        long totalMinutes = totalSeconds / 60;
        long totalHours = totalMinutes / 60;

        String seconds = String.valueOf(totalSeconds % 60);
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }
        String minutes = String.valueOf(totalMinutes % 60);

        if (totalHours == 0) {
            return minutes + ":" + seconds;
        } else {
            if (minutes.length() == 1) {
                minutes = "0" + minutes;
            }
            String hours = String.valueOf(totalHours);
            return hours + ":" + minutes + ":" + seconds;
        }
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duration duration = (Duration) o;
        return milliseconds == duration.milliseconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(milliseconds);
    }

    @Override
    public String toString() {
        return "Duration{" +
                "milliseconds=" + milliseconds +
                '}';
    }
}
