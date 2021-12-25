package ua.edu.cdu.vu.exchangeprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.cdu.vu.exchangeprocessor.component.StatisticsAnalyzer;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.EventProperties;
import ua.edu.cdu.vu.exchangeprocessor.converter.ExchangeEventConverter;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.ExchangeEventDto;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.TransactionType;
import ua.edu.cdu.vu.exchangeprocessor.entity.ExchangeEvent;
import ua.edu.cdu.vu.exchangeprocessor.entity.projection.TransactionAggregationView;
import ua.edu.cdu.vu.exchangeprocessor.exception.EventNotFoundException;
import ua.edu.cdu.vu.exchangeprocessor.repository.ExchangeEventRepository;
import ua.edu.cdu.vu.exchangeprocessor.repository.TransactionRepository;
import ua.edu.cdu.vu.exchangeprocessor.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeEventService {

    private static final int ZERO = 0;
    private final EventProperties eventProperties;
    private final ExchangeEventRepository repository;
    private final TransactionRepository transactionRepository;
    private final StatisticsAnalyzer analyzer;

    public void generateRandomEvent() {
        int oldAmount = calculateActualPrice();
        LocalDateTime dateTime = LocalDate.now().atStartOfDay();
        Map<Integer, Integer> transactionSummary = collectToMap(transactionRepository.getRecentTransactionSummary(dateTime));
        int delta = analyzer.calculateStatisticRandom(transactionSummary);

        int newAmount = oldAmount + delta;
        if (newAmount < eventProperties.getMinValue() || newAmount > eventProperties.getMaxValue()) {
            log.info("Event price has reached its critical value: {}. Price remains the same...", oldAmount);
            return;
        }

        repository.save(ExchangeEvent
                .builder()
                .datePublished(LocalDateTime.now())
                .absoluteAmount(oldAmount + delta)
                .build()
        );
    }

    public List<ExchangeEventDto> getMostSignificantEvents(LocalDate date, Integer limit) {
        LocalDate serverDate = DateUtils.convertTimeZone(date);
        LocalDateTime dateTimeFrom = serverDate.atStartOfDay();
        LocalDateTime dateTimeTo = serverDate.plusDays(1).atStartOfDay();

        List<Long> eventIds = repository.findAllIdsByDatePublishedBetween(dateTimeFrom, dateTimeTo);
        if (eventIds.size() > limit) {
            double correlation = (eventIds.size() - 1.0) / limit;
            eventIds = extractOptionsForNormalDistribution(eventIds, correlation, limit);
        }

        return ExchangeEventConverter.entitiesToDto(repository
                .findAllByDatePublishedBetweenAndIdIn(dateTimeFrom, dateTimeTo, eventIds));
    }

    public ExchangeEventDto getLastExchangeEvent() {
        ExchangeEvent exchangeEvent = repository.findTopByOrderByIdDesc()
                .orElseThrow(EventNotFoundException::new);
        return ExchangeEventConverter.entityToDto(exchangeEvent);
    }

    public Integer calculateActualPrice() {
        return repository.findTopByOrderByIdDesc()
                .map(ExchangeEvent::getAbsoluteAmount)
                .orElse(eventProperties.getInitialValue());
    }

    private List<Long> extractOptionsForNormalDistribution(List<Long> eventIds, double correlation, Integer limit) {
        return DoubleStream
                .iterate(correlation, e -> e + correlation)
                .limit(limit)
                .mapToInt(e -> (int) Math.round(e))
                .mapToObj(eventIds::get)
                .collect(Collectors.toList());
    }

    private Map<Integer, Integer> collectToMap(List<TransactionAggregationView> views) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(TransactionType.BUY.correlation(), ZERO);
        map.put(TransactionType.SELL.correlation(), ZERO);

        return views
                .stream()
                .collect(Collectors.toMap(view -> view.getTransactionType().correlation(),
                        TransactionAggregationView::getTransactionCount, (v1, v2) -> v2, () -> map));
    }
}
