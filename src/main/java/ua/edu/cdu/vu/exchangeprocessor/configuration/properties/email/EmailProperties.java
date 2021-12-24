package ua.edu.cdu.vu.exchangeprocessor.configuration.properties.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.mail")
public class EmailProperties {
    private EmailDetails visitationMemo;
    private EmailDetails invitation;
}
