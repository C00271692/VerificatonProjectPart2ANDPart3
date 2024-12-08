package cm;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class RateTest2 {

    @Test
    void testConstructorValidInputs() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");

        Rate rate = new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, normalRate, reducedRate);
        assertNotNull(rate);
    }

    @Test
    void testConstructorInvalidInputs() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(8, 12)); // Overlapping period
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");

        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, normalRate, reducedRate));
    }

    @Test
    void testConstructorInvalidRates() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));

        // Invalid reducedRate (> 10)
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal invalidReducedRate = new BigDecimal("11");
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, normalRate, invalidReducedRate));
    }

    @Test
    void testConstructorNullPeriods() {
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, null, new ArrayList<>(), normalRate, reducedRate));
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, new ArrayList<>(), null, normalRate, reducedRate));
    }

    @Test
    void testConstructorNullRates() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));

        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, null, new BigDecimal("2")));
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, new BigDecimal("5"), null));
    }

    @Test
    void testConstructorNegativeRates() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));

        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, new BigDecimal("-5"), new BigDecimal("2")));
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, new BigDecimal("5"), new BigDecimal("-2")));
    }

    @Test
    void testConstructorNormalRateEqualToReducedRate() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));

        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, new BigDecimal("2"), new BigDecimal("2")));
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, new BigDecimal("2"), new BigDecimal("2")));
    }

    @Test
    void testConstructorValidNoOverlappingPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(12, 14));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));
        normalPeriods.add(new Period(14, 16));
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");


        // This should not throw an exception as the periods do NOT overlap
        assertDoesNotThrow(() -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, normalRate, reducedRate));
    }

    @Test
    void testConstructorOverlappingReducedPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(9, 12)); // Overlapping period
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(12, 14));
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");

        // This should throw an exception due to overlapping reduced periods
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, normalRate, reducedRate));
    }

    @Test
    void testConstructorOverlappingNormalPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));
        normalPeriods.add(new Period(11, 14)); // Overlapping period
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");

        // This should throw an exception due to overlapping normal periods
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, normalRate, reducedRate));
    }

    @Test
    void testCalculate() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");

        Rate rate = new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, normalRate, reducedRate);
        Period periodStay = new Period(7, 12);
        BigDecimal expected = new BigDecimal("16"); // 3 hours reduced rate + 2 hours normal rate
        assertEquals(expected, rate.calculate(periodStay));
    }
    //Old testCaluclateMethod from part 2 of the verification project
    /*@Test
    void testCalculateVisitor() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");

        Rate rate = new Rate(CarParkKind.VISITOR, reducedPeriods, normalPeriods, normalRate, reducedRate);
        Period periodStay = new Period(7, 12);

        BigDecimal expected = BigDecimal.ZERO; // Visitor rate is zero
        assertEquals(expected, rate.calculate(periodStay));
    }  */

    @Test
    void testCalculateVisitor() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");

        Rate rate = new Rate(CarParkKind.VISITOR, reducedPeriods, normalPeriods, normalRate, reducedRate);
        Period periodStay = new Period(7, 12);

        BigDecimal expected = new BigDecimal("3.0");
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculateManagement() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 12));
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal reducedRate = new BigDecimal("2");

        Rate rate = new Rate(CarParkKind.MANAGEMENT, reducedPeriods, normalPeriods, normalRate, reducedRate);
        Period periodStay = new Period(7, 8);

        BigDecimal expected = new BigDecimal("4"); //3 hrs reduced rate @2 + 2 hours normal rate @5
        assertEquals(expected, rate.calculate(periodStay));
    }

}



