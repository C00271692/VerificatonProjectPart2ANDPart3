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

        // Invalid reducedRate (greater than 10)
        BigDecimal normalRate = new BigDecimal("5");
        BigDecimal invalidReducedRate = new BigDecimal("11");
        assertThrows(IllegalArgumentException.class, () -> new Rate(CarParkKind.STANDARD, reducedPeriods, normalPeriods, normalRate, invalidReducedRate));
    }
}
