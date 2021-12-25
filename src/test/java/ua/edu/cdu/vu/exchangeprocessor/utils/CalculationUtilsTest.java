package ua.edu.cdu.vu.exchangeprocessor.utils;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CalculationUtilsTest {
    @Test
    void generateRandomNumber() {
        boolean result = IntStream
                .generate(() -> CalculationUtils.generateRandomNumber(-1, 1))
                .limit(100)
                .allMatch(num -> num >= -1 && num <= 1);
        assertTrue(result);
    }
}
