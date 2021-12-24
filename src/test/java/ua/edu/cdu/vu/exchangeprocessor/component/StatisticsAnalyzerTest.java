package ua.edu.cdu.vu.exchangeprocessor.component;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class StatisticsAnalyzerTest {

    private static final StatisticsAnalyzer ANALYZER = new StatisticsAnalyzer();

    @Test
    void shouldCalculateStatisticRandom() {
        Map<Integer, Integer> statisticSummary = new HashMap<>(Map.of(-1, 5, 1, 15));

        IntStream
                .range(0, 100)
                .forEach(e -> assertThat(ANALYZER.calculateStatisticRandom(statisticSummary))
                        .isCloseTo(0, Offset.offset(1)));

    }
}
