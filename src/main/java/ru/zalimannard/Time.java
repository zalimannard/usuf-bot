package ru.zalimannard;

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


    /**
     * Instantiates a new Time. The default value is 0 milliseconds.
     */
    public Time() {

    }

    /**
     * Instantiates a new Time. Sets the argument as a value. If the argument is incorrect, sets the value to 0.
     *
     * @param milliseconds the time value in milliseconds
     */
    public Time(Long milliseconds) {

    }

    /**
     * Instantiates a new Time. Copy constructor. If the other is null, the value is set to 0.
     *
     * @param other the time to be copied
     */
    public Time(Time other) {

    }

    /**
     * Instantiates a new Time. If the value is correct, then it is set. Otherwise, the value is set to 0.
     *
     * @param timeAsString the value that the object may be initialized with
     */
    public Time(String timeAsString) {

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
        return null;
    }

    /**
     * Adds the specified value to the current time. If the other is null, the value is set to 0.
     *
     * @param other the value to add
     */
    public void add(Time other) {

    }

    /**
     * Subtracts the specified number from the current number. If the value is null or less than 0, it becomes 0.
     *
     * @param other the time to be subtracted from the current time
     */
    public void sub(Time other) {

    }

    /**
     * Gets milliseconds.
     *
     * @return the time in milliseconds
     */
    public Long getMilliseconds() {
        return null;
    }

    /**
     * Gets hours:minutes:seconds format of time.
     *
     * @return the time in the format (H...)H:MM:SS
     */
    public String getHmsFormat() {
        return null;
    }

    /**
     * Gets minutes:seconds format of time.
     *
     * @return the time in the format (M)M:SS
     */
    public String getMsFormat() {
        return null;
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
