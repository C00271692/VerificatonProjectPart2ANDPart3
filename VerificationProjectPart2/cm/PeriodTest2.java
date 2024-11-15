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

    //Tetst for the isIn method:
    @Test
    //
    void testIsIn() throws Exception {
        Period period = new Period(8, 12);
        //Retrieving the private method isIn from the Period class
        //we take the name of the method and its parameter type as arguments
        Method isInMethod = Period.class.getDeclaredMethod("isIn", int.class);
        //bypass Javas access control checker
        isInMethod.setAccessible(true);

        assertTrue((Boolean) isInMethod.invoke(period, 10));
        assertFalse((Boolean) isInMethod.invoke(period, 12));
        assertFalse((Boolean) isInMethod.invoke(period, 7));
    }

    @Test
    void testIsInWithList() throws Exception {
        Period period1 = new Period(8, 12);
        Period period2 = new Period(14, 18);
        List<Period> periods = Arrays.asList(period1, period2);

        Method isInMethod = Period.class.getDeclaredMethod("isIn", int.class, List.class);
        isInMethod.setAccessible(true);

        assertTrue((Boolean) isInMethod.invoke(null, 10, periods));
        assertFalse((Boolean) isInMethod.invoke(null, 13, periods));
        assertTrue((Boolean) isInMethod.invoke(null, 14, periods));
    }
    //Tests for Occurence method:
    @Test
    void testOccurences() {
        Period period1 = new Period(8, 12);
        Period period2 = new Period(10, 14);
        List<Period> periods = Arrays.asList(period1, period2);
        //suspect bug in the original code.
        assertEquals(2, period1.occurences(periods));
        assertEquals(2, period2.occurences(periods));
    }


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