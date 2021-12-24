package ua.edu.cdu.vu.exchangeprocessor.configuration.properties.google;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.google.urls")
public class GoogleApiProperties {
    private String contactUri;
}
