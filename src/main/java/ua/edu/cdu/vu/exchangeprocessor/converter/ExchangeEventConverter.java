package ua.edu.cdu.vu.exchangeprocessor.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.ExchangeEventDto;
import ua.edu.cdu.vu.exchangeprocessor.entity.ExchangeEvent;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeEventConverter {

    public static ExchangeEventDto entityToDto(ExchangeEvent exchangeEvent) {
        return ExchangeEventDto
                .builder()
                .dateTime(exchangeEvent.getDatePublished())
                .absoluteAmount(exchangeEvent.getAbsoluteAmount())
                .build();
    }

    public static List<ExchangeEventDto> entitiesToDto(List<ExchangeEvent> exchangeEvent) {
        return exchangeEvent
                .stream()
                .map(ExchangeEventConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
