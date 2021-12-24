package ua.edu.cdu.vu.exchangeprocessor.configuration.properties.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDetails {
    private String subject;
    private String template;
}
