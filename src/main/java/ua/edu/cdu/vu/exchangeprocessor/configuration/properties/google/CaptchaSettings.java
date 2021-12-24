package ua.edu.cdu.vu.exchangeprocessor.configuration.properties.google;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "google.recaptcha.credentials")
public class CaptchaSettings {
    private String site;
    private String secret;
}
