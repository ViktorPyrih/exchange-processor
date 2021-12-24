package ua.edu.cdu.vu.exchangeprocessor.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.RetryProperties;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.google.GoogleApiProperties;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleApiClient {

    private final ObjectMapper objectMapper;
    private final RetryProperties retryProperties;
    private final GoogleApiProperties googleApiProperties;
    private final WebClient rest;

    public List<String> getPrincipalContactsEmails() {
        return rest
                .get()
                .uri(googleApiProperties.getContactUri())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractContactEmails)
                .retryWhen(Retry.backoff(retryProperties.getMaxAttempts(), Duration.ofSeconds(retryProperties.getInitialSeconds())))
                .doOnError(e -> log.error("Google contact api can't be reached...", e))
                .onErrorReturn(Collections.emptyList())
                .block();
    }

    private List<String> extractContactEmails(String contacts) {
        List<String> contactEmails = Collections.emptyList();
        try {
            contactEmails = objectMapper.readTree(contacts).findValuesAsText("value");
        } catch (JsonProcessingException e) {
            log.error("Can't extract contact emails from google contact response");
        }

        return contactEmails;
    }
}
