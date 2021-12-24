package ua.edu.cdu.vu.exchangeprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRecaptchaException extends RuntimeException {
    public InvalidRecaptchaException(String message) {
        super(message);
    }
}
