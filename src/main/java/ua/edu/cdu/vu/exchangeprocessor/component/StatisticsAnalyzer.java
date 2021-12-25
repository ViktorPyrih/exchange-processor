package ua.edu.cdu.vu.exchangeprocessor.component;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ua.edu.cdu.vu.exchangeprocessor.utils.CalculationUtils;

import java.util.Map;

@Component
public class StatisticsAnalyzer {

    private static final int ZERO = 0;

    public int calculateStatisticRandom(Map<Integer, Integer> recentSummary) {
        return calculateStatisticRandom(recentSummary, ZERO);
    }

    public int calculateStatisticRandom(Map<Integer, Integer> recentSummary, int pivot) {
        if (CollectionUtils.isEmpty(recentSummary)) {
            return pivot;
        }

        long sum = calculateStatisticSum(recentSummary);
        long optimizationFactor = calculateOptimizationFactor(recentSummary, sum);
        sum /= optimizationFactor;
        long pivotPart = sum / recentSummary.size();
        long randomizerSize = sum + pivotPart;
        if (randomizerSize > Integer.MAX_VALUE || randomizerSize < ZERO) {
            throw new IllegalArgumentException("Statics summary has too many elements to perform calculation");
        }

        recentSummary.put(pivot, (int) pivotPart);
        int[] randomizer = aggregateToArray(recentSummary, (int) randomizerSize, (int) optimizationFactor);

        return randomizer[CalculationUtils.generateRandomNumber(0, (int) randomizerSize - 1)];
    }

    private long calculateStatisticSum(Map<Integer, Integer> recentSummary) {
        return recentSummary
                .values()
                .stream()
                .mapToLong(e -> e)
                .sum();
    }

    private int[] aggregateToArray(Map<Integer, Integer> map, int size, int optimizationFactor) {
        int[] array = new int[size];
        int currentIndex = 0;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            for (int i = 0; i < entry.getValue() / optimizationFactor; i++) {
                array[currentIndex++] = entry.getKey();
            }
        }

        return array;
    }

    private long calculateOptimizationFactor(Map<Integer, Integer> map, long sum) {
        long optimizationFactor = sum;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            if (optimizationFactor == 1) {
                break;
            }
            optimizationFactor = CalculationUtils.calculateGreatestCommonDivisor(optimizationFactor, entry.getValue());
        }

        return optimizationFactor;
    }
}
