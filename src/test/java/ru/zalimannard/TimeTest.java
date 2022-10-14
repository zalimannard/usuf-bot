package ru.zalimannard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TimeTest")
class TimeTest {

    @Test
    void constructor_withoutArgument_time0msCreated() {
        Time time = new Time();

        assertEquals(0L, time.getMilliseconds());
    }

    @Test
    void constructor_with42msArgument_time42msCreated() {
        Time time = new Time(42L);

        assertEquals(42L, time.getMilliseconds());
    }

    @Test
    void constructor_withNegative42msArgument_time0msCreated() {
        Time time = new Time(-42L);

        assertEquals(0L, time.getMilliseconds());
    }

    @Test
    void constructor_withLongNullArgument_time0msCreated() {
        Time time = new Time((Long) null);

        assertEquals(0L, time.getMilliseconds());
    }

    @Test
    void constructor_withOtherTime99msArgument_time99msCreated() {
        Time time1 = new Time(99L);
        Time time2 = new Time(time1);

        assertEquals(99L, time2.getMilliseconds());
    }

    @Test
    void constructor_withOtherTimeNullArgument_time0msCreated() {
        Time time1 = null;
        Time time2 = new Time(time1);

        assertEquals(0L, time2.getMilliseconds());
    }

    @Test
    void constructor_withString5h00m03s876msArgument_time18003876msCreated() {
        String stringTime = "5:00:03.876";
        Time time = new Time(stringTime);

        assertEquals(18003876L, time.getMilliseconds());
    }

    @Test
    void constructor_withString5h00m3s876msArgument_time0msCreated() {
        String stringTime = "5:00:3.876";
        Time time = new Time(stringTime);

        assertEquals(0L, time.getMilliseconds());
    }

    @Test
    void constructor_withString68minArgument_time0msCreated() {
        String stringTime = "68:00.876";
        Time time = new Time(stringTime);

        assertEquals(0L, time.getMilliseconds());
    }

    @Test
    void constructor_withNullStringArgument_time0msCreated() {
        String stringTime = null;
        Time time = new Time(stringTime);

        assertEquals(0L, time.getMilliseconds());
    }

    @Test
    void isCorrectTime_0h00m00s000ms_trueReturned() {
        String value = "0:00:00.000";

        assertTrue(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_00h01m10s_trueReturned() {
        String value = "00:01:10";

        assertTrue(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_05m00s123ms_trueReturned() {
        String value = "05:00.123";

        assertTrue(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_5m23s_trueReturned() {
        String value = "5:23";

        assertTrue(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_23s076ms_trueReturned() {
        String value = "23.076";

        assertTrue(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_23s_trueReturned() {
        String value = "23";

        assertTrue(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_23s1000ms_falseReturned() {
        String value = "23.1000";

        assertFalse(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_66s_falseReturned() {
        String value = "66";

        assertFalse(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_504m06s_falseReturned() {
        String value = "504:06";

        assertFalse(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_500h50m06s_trueReturned() {
        String value = "500:50:06";

        assertTrue(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_null_falseReturned() {
        String value = null;

        assertFalse(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_0s500ms500e_falseReturned() {
        String value = "0.500.500";

        assertFalse(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_0h50m50s50e_falseReturned() {
        String value = "0:50:50:50";

        assertFalse(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_negative50s_falseReturned() {
        String value = "-50";

        assertFalse(Time.isCorrectTime(value));
    }

    @Test
    void isCorrectTime_AmBBs_falseReturned() {
        String value = "a:bb";

        assertFalse(Time.isCorrectTime(value));
    }

    @Test
    void add_time1msPlusTime4ms_time5msReturned() {
        Time time1 = new Time(1L);
        Time time2 = new Time(4L);

        time1.add(time2);

        assertEquals(5L, time1.getMilliseconds());
    }

    @Test
    void add_time1msPlusNull_time1msReturned() {
        Time time1 = new Time(1L);
        Time time2 = null;

        time1.add(time2);

        assertEquals(1L, time1.getMilliseconds());
    }

    @Test
    void sub_time10msMinusTime4ms_time6msReturned() {
        Time time1 = new Time(10L);
        Time time2 = new Time(4L);

        time1.sub(time2);

        assertEquals(6L, time1.getMilliseconds());
    }

    @Test
    void sub_time10msMinusTime16ms_time0msReturned() {
        Time time1 = new Time(10L);
        Time time2 = new Time(16L);

        time1.sub(time2);

        assertEquals(0L, time1.getMilliseconds());
    }

    @Test
    void sub_time10msMinusNull_time10msReturned() {
        Time time1 = new Time(10L);
        Time time2 = null;

        time1.sub(time2);

        assertEquals(10L, time1.getMilliseconds());
    }

    @Test
    void getMilliseconds_time5ms_5msReturned() {
        Time time = new Time(5L);

        assertEquals(5L, time.getMilliseconds());
    }

    @Test
    void getHmsFormat_5m56s800ms_0h05mReturned() {
        Time time = new Time("5:56.800");

        assertEquals("0:05:56", time.getHmsFormat());
    }

    @Test
    void getHmsFormat_888h05m56s800ms_0h05mReturned() {
        Time time = new Time("800:05:56.800");

        assertEquals("800:05:56", time.getHmsFormat());
    }

    @Test
    void getMsFormat_56s800ms_0m56sReturned() {
        Time time = new Time("56.800");

        assertEquals("0:56", time.getMsFormat());
    }

    @Test
    void getMsFormat_5m56s800ms_5m56sReturned() {
        Time time = new Time("5:56.800");

        assertEquals("5:56", time.getMsFormat());
    }

    @Test
    void getMsFormat_6h05m56s800ms_5m56sReturned() {
        Time time = new Time("6:05:56.800");

        assertEquals("5:56", time.getMsFormat());
    }
}