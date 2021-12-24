package ua.edu.cdu.vu.exchangeprocessor.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.user")
public class UserProperties {
    private Integer balance;
    private Integer stock;
    private Integer giftDays;
}
