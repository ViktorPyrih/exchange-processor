package ua.edu.cdu.vu.exchangeprocessor.configuration.properties.scheduling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.scheduling")
public class SchedulingProperties {
    private JobProperties exchange;
    private JobProperties gift;
    private JobProperties visitMemo;
}
