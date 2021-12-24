package ua.edu.cdu.vu.exchangeprocessor;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.EventProperties;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.email.EmailProperties;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.google.GoogleApiProperties;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.google.GoogleProperties;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.UserProperties;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.RetryProperties;

@SpringBootApplication
@EnableTransactionManagement
@EnableEncryptableProperties
@EnableConfigurationProperties({GoogleProperties.class, UserProperties.class, EventProperties.class,
        RetryProperties.class, GoogleApiProperties.class, EmailProperties.class})
public class ExchangeProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeProcessorApplication.class, args);
    }
}
