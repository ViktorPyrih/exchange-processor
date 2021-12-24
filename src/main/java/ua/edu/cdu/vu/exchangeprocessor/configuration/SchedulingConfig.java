package ua.edu.cdu.vu.exchangeprocessor.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(prefix = "app.scheduling", value = "enabled")
@EnableScheduling
@Configuration
public class SchedulingConfig {
}
