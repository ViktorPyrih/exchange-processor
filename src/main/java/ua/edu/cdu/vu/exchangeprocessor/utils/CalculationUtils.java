package ua.edu.cdu.vu.exchangeprocessor.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CalculationUtils {

    public static int generateRandomNumber(int lowerBound, int upperBound) {
        return (int) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
    }

    public static long calculateGreatestCommonDivisor(long a, long b) {
        while (a != 0 && b != 0) {
            if (a >= b) {
                a = a % b;
            } else {
                b = b % a;
            }
        }

        return (a + b);
    }
}
