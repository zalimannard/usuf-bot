package ru.zalimannard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DurationTest")
class DurationTest {

    @Test
    void constructor_withoutArgument_duration0msCreated() {
        Duration duration = new Duration();

        assertEquals(0, duration.getMilliseconds());
    }

    @Test
    void constructor_positiveNumber_appropriateDurationCreated() {
        Duration duration = new Duration(42);

        assertEquals(42, duration.getMilliseconds());
    }

    @Test
    void constructor_negativeNumber_duration0msCreated() {
        Duration duration = new Duration(-42);

        assertEquals(0, duration.getMilliseconds());
    }

    @Test
    void constructor_stringInHhmmssFormat_appropriateDurationCreated() {
        String value = "10:10:40";
        Duration duration = new Duration(value);

        assertEquals(36640000, duration.getMilliseconds());
    }

    @Test
    void constructor_stringInHhmmmssFormat_exceptionWasThrown() {
        String value = "10:100:40";

        assertThrows(IllegalArgumentException.class, () -> new Duration(value));
    }

    @Test
    void isCorrectDuration_stringInHhmmssFormat_trueReturned() {
        String value = "00:01:10";

        assertTrue(Duration.isCorrectDuration(value));
    }

    @Test
    void isCorrectDuration_stringInMssFormat_trueReturned() {
        String value = "5:23";

        assertTrue(Duration.isCorrectDuration(value));
    }

    @Test
    void isCorrectDuration_stringInSsFormat_trueReturned() {
        String value = "23";

        assertTrue(Duration.isCorrectDuration(value));
    }

    @Test
    void isCorrectDuration_stringInSsFormat_falseReturned() {
        String value = "66";

        assertFalse(Duration.isCorrectDuration(value));
    }

    @Test
    void isCorrectDuration_stringInMmmssFormat_falseReturned() {
        String value = "504:06";

        assertFalse(Duration.isCorrectDuration(value));
    }

    @Test
    void isCorrectDuration_stringInHhhmmssFormat_trueReturned() {
        String value = "500:50:06";

        assertTrue(Duration.isCorrectDuration(value));
    }

    @Test
    void isCorrectDuration_stringInXhhmmssFormat_falseReturned() {
        String value = "0:50:50:50";

        assertFalse(Duration.isCorrectDuration(value));
    }

    @Test
    void isCorrectDuration_stringInNegativeSsFormat_falseReturned() {
        String value = "-50";

        assertFalse(Duration.isCorrectDuration(value));
    }

    @Test
    void isCorrectDuration_stringHmssFormat_falseReturned() {
        String value = "1:1:00";

        assertFalse(Duration.isCorrectDuration(value));
    }

    @Test
    void add_twoUsualDurations_correctSumReturned() {
        Duration duration1 = new Duration(1);
        Duration duration2 = new Duration(4);

        duration1.add(duration2);

        assertEquals(5, duration1.getMilliseconds());
    }

    @Test
    void sub_bigDurationMinusSmallDuration_correctValueReturned() {
        Duration duration1 = new Duration(10);
        Duration duration2 = new Duration(4);

        duration1.sub(duration2);

        assertEquals(6, duration1.getMilliseconds());
    }

    @Test
    void sub_smallDurationMinusBigDuration_duration0msReturned() {
        Duration duration1 = new Duration(4);
        Duration duration2 = new Duration(10);

        duration1.sub(duration2);

        assertEquals(0, duration1.getMilliseconds());
    }

    @Test
    void getMilliseconds_duration5ms_5msReturned() {
        Duration duration = new Duration(5);

        assertEquals(5, duration.getMilliseconds());
    }

    @Test
    void getHmsFormat_durationWithSingleDigitOfMinutes_correctStringInMssFormatReturned() {
        Duration duration = new Duration(66000);

        assertEquals("1:06", duration.getHmsFormat());
    }

    @Test
    void getHmsFormat_durationWithSingleDigitOfHours_correctStringInMssFormatReturned() {
        Duration duration = new Duration(3970000);

        assertEquals("1:06:10", duration.getHmsFormat());
    }
}