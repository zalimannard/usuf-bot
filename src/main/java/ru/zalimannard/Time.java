package ru.zalimannard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * The type Time. Can only contain non-negative non-null time values
 */
public class Time {
    /**
     * The constant ZERO.
     */
    public static final Time ZERO = new Time(0L);
    /**
     * The constant SECOND.
     */
    public static final Time SECOND = new Time(1000L);
    /**
     * The constant MINUTE.
     */
    public static final Time MINUTE = new Time(60000L);
    /**
     * The constant HOUR.
     */
    public static final Time HOUR = new Time(3600000L);

    private Long milliseconds;
    private static Logger logger = LogManager.getRootLogger();

    /**
     * Instantiates a new Time. The default value is 0 milliseconds.
     */
    public Time() {
        setMilliseconds(0L);
    }

    /**
     * Instantiates a new Time. Sets the argument as a value. If the argument is incorrect, sets the value to 0.
     *
     * @param milliseconds the time value in milliseconds
     */
    public Time(Long milliseconds) {
        if (milliseconds == null) {
            setMilliseconds(0L);
        } else if (milliseconds < 0L) {
            setMilliseconds(0L);
        } else {
            setMilliseconds(milliseconds);
        }
    }

    /**
     * Instantiates a new Time. Copy constructor. If the other is null, the value is set to 0.
     *
     * @param other the time to be copied
     */
    public Time(Time other) {
        if (other == null) {
            setMilliseconds(0L);
        } else {
            setMilliseconds(other.getMilliseconds());
        }
    }

    /**
     * Instantiates a new Time. If the value is correct, then it is set. Otherwise, the value is set to 0.
     *
     * @param timeAsString the value that the object may be initialized with
     */
    public Time(String timeAsString) {
        if (isCorrectTime(timeAsString)) {
            setMilliseconds(timeAsStringToLong(timeAsString));
        } else {
            setMilliseconds(0L);
        }
    }

    /**
     * Check the string for the correct time format.
     *
     * @param value the string that needs to be checked for the correct time format
     * @return returns true if the number satisfies one of the formats: (H...)H:MM:SS.MMM, (H...)H:MM:SS, (M)M:SS.MMM,
     * (M)M:SS, (S)S.MMM, (S)S. Hours - any number, minutes less than 60, seconds less than 60, milliseconds less than
     * 1000, all not less than 0.
     */
    public static Boolean isCorrectTime(String value) {
        if (value == null) {
            logger.debug("Checking whether null is a date");
            return false;
        }

        String[] hmsAndMsec = value.split("\\.");
        if ((hmsAndMsec.length < 1) || (hmsAndMsec.length > 2)) {
            logger.debug("Incorrect number of '.': " + value);
            return false;
        }
        if (hmsAndMsec.length == 2) {
            String milliseconds = hmsAndMsec[1];
            if ((milliseconds.length() != 3) || (!Utils.isNonNegativeIntegerNumber(milliseconds))) {
                logger.debug("Milliseconds are specified incorrectly: " + value);
                return false;
            }
        }

        String[] hms = hmsAndMsec[0].split(":");
        if ((hms.length < 1) || (hms.length > 3)) {
            logger.debug("Incorrect number of time parts: " + value);
            return false;
        }
        for (String part : hms) {
            if (!Utils.isNonNegativeIntegerNumber(part)) {
                logger.debug("Some of the values is not a valid number: " + value);
                return false;
            }
        }
        String seconds = hms[hms.length - 1];
        if ((hms.length > 1) && (seconds.length() != 2)) {
            logger.debug("Incorrectly written seconds" + value);
            return false;
        }
        if (Long.parseLong(seconds) >= 60) {
            logger.debug("Exceeding the possible number of seconds: " + value);
            return false;
        }
        if (hms.length >= 2) {
            String minutes = hms[hms.length - 2];
            if ((hms.length > 2) && (minutes.length() != 2)) {
                logger.debug("Incorrectly written minutes: " + value);
                return false;
            }
            if (Long.parseLong(minutes) >= 60) {
                logger.debug("Exceeding the possible number of minutes: " + value);
                return false;
            }
        }

        return true;
    }

    /**
     * Adds the specified value to the current time. If the other is null, the value is set to 0.
     *
     * @param other the value to add
     */
    public void add(Time other) {
        if (other != null) {
            setMilliseconds(getMilliseconds() + other.getMilliseconds());
        }
    }

    /**
     * Subtracts the specified number from the current number. If the value is null or less than 0, it becomes 0.
     *
     * @param other the time to be subtracted from the current time
     */
    public void sub(Time other) {
        if (other != null) {
            Long newMillisecondsValue = Math.max(0L, getMilliseconds() - other.getMilliseconds());
            setMilliseconds(newMillisecondsValue);
        }
    }

    /**
     * Gets milliseconds.
     *
     * @return the time in milliseconds
     */
    public Long getMilliseconds() {
        return milliseconds;
    }

    /**
     * Gets hours:minutes:seconds format of time.
     *
     * @return the time in the format (H...)H:MM:SS
     */
    public String getHmsFormat() {
        Long fullSeconds = getMilliseconds() / 1000;
        Long fullMinutes = fullSeconds / 60;
        Long fullHours = fullMinutes / 60;

        String seconds = String.valueOf(fullSeconds % 60);
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }
        String minutes = String.valueOf(fullMinutes % 60);
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }
        String hours = String.valueOf(fullHours);

        return hours + ":" + minutes + ":" + seconds;
    }

    /**
     * Gets minutes:seconds format of time.
     *
     * @return the time in the format (M)M:SS
     */
    public String getMsFormat() {
        Long fullSeconds = getMilliseconds() / 1000;
        Long fullMinutes = fullSeconds / 60;

        String seconds = String.valueOf(fullSeconds % 60);
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }
        String minutes = String.valueOf(fullMinutes % 60);

        return minutes + ":" + seconds;
    }

    private void setMilliseconds(Long value) {
        milliseconds = value;
    }

    private Long timeAsStringToLong(String value) {
        if (!isCorrectTime(value)) {
            return 0L;
        }

        Long answer = 0L;
        String[] hmsAndMsec = value.split("\\.");
        if (hmsAndMsec.length == 2) {
            answer += Long.parseLong(hmsAndMsec[1]);
        }
        String[] hms = hmsAndMsec[0].split(":");
        answer += Long.parseLong(hms[hms.length - 1]) * 1000;
        if (hms.length >= 2) {
            answer += Long.parseLong(hms[hms.length - 2]) * 60 * 1000;
        }
        if (hms.length == 3) {
            answer += Long.parseLong(hms[hms.length - 3]) * 60 * 60 * 1000;
        }

        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return Objects.equals(getMilliseconds(), time.getMilliseconds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMilliseconds());
    }

    @Override
    public String toString() {
        return "Time{" +
                "milliseconds=" + milliseconds +
                '}';
    }
}
