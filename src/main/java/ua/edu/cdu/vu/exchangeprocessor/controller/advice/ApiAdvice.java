package ua.edu.cdu.vu.exchangeprocessor.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.edu.cdu.vu.exchangeprocessor.converter.ValidationErrorConverter;
import ua.edu.cdu.vu.exchangeprocessor.dto.advice.ValidationErrorResponse;
import ua.edu.cdu.vu.exchangeprocessor.exception.EventNotFoundException;
import ua.edu.cdu.vu.exchangeprocessor.exception.InvalidRecaptchaException;
import ua.edu.cdu.vu.exchangeprocessor.dto.advice.GenericResponse;
import ua.edu.cdu.vu.exchangeprocessor.exception.TransactionFailedException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApiAdvice {

    private static final String INVALID_RECAPTCHA = "InvalidRecaptcha";
    private static final String TRANSACTION_FAILED = "TransactionFailed";

    @ExceptionHandler(InvalidRecaptchaException.class)
    public ResponseEntity<GenericResponse> handleInvalidRecaptcha(InvalidRecaptchaException exception) {
        return ResponseEntity
                .badRequest()
                .body(GenericResponse
                        .builder()
                        .error(INVALID_RECAPTCHA)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<GenericResponse> handleTransactionFailed(TransactionFailedException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(GenericResponse
                        .builder()
                        .error(TRANSACTION_FAILED)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<GenericResponse> handleEventNotFound() {
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {
        return ResponseEntity.badRequest().body(
                ValidationErrorConverter.convert(exception));
    }
}
