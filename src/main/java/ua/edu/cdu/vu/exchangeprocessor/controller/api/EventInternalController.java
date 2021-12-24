package ua.edu.cdu.vu.exchangeprocessor.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.ExchangeEventDto;
import ua.edu.cdu.vu.exchangeprocessor.service.ExchangeEventService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RequestMapping("/internal/events")
@RestController
@RequiredArgsConstructor
public class EventInternalController {

    private final ExchangeEventService exchangeEventService;

    @GetMapping("/all")
    public List<ExchangeEventDto> getMostSignificantExchangeEvents(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "date") LocalDate date,
            @RequestParam(value = "limit", defaultValue = "30") @Min(10) @Max(50) Integer limit) {

        log.info("Requested to return exchange events. Limit: {}", limit);
        List<ExchangeEventDto> events = exchangeEventService.getMostSignificantEvents(date, limit);
        log.info("Found {} event records", events.size());

        return events;
    }

    @GetMapping("/last")
    public ExchangeEventDto getLastExchangeEvents() {
        log.info("Requested to return last exchange event");
        return exchangeEventService.getLastExchangeEvent();
    }
}
