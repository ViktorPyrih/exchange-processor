package ua.edu.cdu.vu.exchangeprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TransactionFailedException extends RuntimeException {

    public TransactionFailedException(String message) {
        super(message);
    }
}
