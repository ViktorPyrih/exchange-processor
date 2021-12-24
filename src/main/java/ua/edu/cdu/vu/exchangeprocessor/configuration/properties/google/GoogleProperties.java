package ua.edu.cdu.vu.exchangeprocessor.configuration.properties.google;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "google.recaptcha.destination")
public class GoogleProperties {
    private String url;
    private String key;
}
