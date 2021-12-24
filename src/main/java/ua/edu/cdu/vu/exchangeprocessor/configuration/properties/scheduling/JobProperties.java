package ua.edu.cdu.vu.exchangeprocessor.configuration.properties.scheduling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobProperties {
    private String cron;
    private String zone;
}
