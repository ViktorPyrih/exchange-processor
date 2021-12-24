package ua.edu.cdu.vu.exchangeprocessor.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.ExchangeEventDto;
import ua.edu.cdu.vu.exchangeprocessor.repository.ExchangeEventRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ExchangeEventServiceTest {

    @Autowired
    private ExchangeEventService exchangeEventService;

    @MockBean
    private ExchangeEventRepository exchangeEventRepository;

    @Test
    void getMostSignificantEventsTest() {
        when(exchangeEventRepository.findAllIdsByDatePublishedBetween(any(), any()))
                .thenReturn(LongStream.rangeClosed(1, 35)
                        .boxed()
                        .collect(Collectors.toList()));
        List<ExchangeEventDto> events = exchangeEventService.getMostSignificantEvents(LocalDate.now(), 10);
        assertThat(events).isNotNull();
    }
}
