package ua.edu.cdu.vu.exchangeprocessor.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.event")
public class EventProperties {
    private Integer initialValue;
    private Integer minValue;
    private Integer maxValue;
}
