package cm;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PeriodTest2 {

    //Tests for the Period class:
    @Test
    void testValidPeriod() {
        Period period = new Period(8, 12);
        assertNotNull(period);
    }

    @Test
    void testInvalidPeriod() {
        assertThrows(IllegalArgumentException.class, () -> new Period(12, 8));
    }
    @Test
    void testInvalidPeriod2() {
        assertThrows(IllegalArgumentException.class, () -> new Period(-1, 10));
    }
    @Test
    void testInvalidPeriod3() {
        assertThrows(IllegalArgumentException.class, () -> new Period(10, 25)); }
    @Test
    void testInvalidPeriod4() {
        assertThrows(IllegalArgumentException.class, () -> new Period(24, 25)); }



    //Tests for the overlaps method:
    @Test
    void testOverlaps() {
        Period period1 = new Period(8, 12);
        Period period2 = new Period(10, 14);
        assertTrue(period1.overlaps(period2));
    }

    @Test
    void testOverlaps2() {
        Period period1 = new Period(8, 12);
        Period period3 = new Period(12, 16);
        assertFalse(period1.overlaps(period3));
    }

    @Test
    void testDuration() {
        Period period = new Period(8, 12);
        assertEquals(4, period.duration());
    }

    @Test
    void testInvalidDuration() {
        assertThrows(IllegalArgumentException.class, () -> new Period(12, 8));
    }}