package ua.edu.cdu.vu.exchangeprocessor.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.retry")
public class RetryProperties {
    private Integer maxAttempts;
    private Integer initialSeconds;
}
