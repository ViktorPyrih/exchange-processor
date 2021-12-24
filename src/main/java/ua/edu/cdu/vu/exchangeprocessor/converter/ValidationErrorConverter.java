package ua.edu.cdu.vu.exchangeprocessor.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import ua.edu.cdu.vu.exchangeprocessor.dto.advice.ValidationErrorResponse;
import ua.edu.cdu.vu.exchangeprocessor.dto.advice.Violation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationErrorConverter {

    private static final String ERROR_CODE = "ConstrainViolation";

    public static ValidationErrorResponse convert(ConstraintViolationException exception) {
        return ValidationErrorResponse
                .builder()
                .error(ERROR_CODE)
                .violations(exception.getConstraintViolations()
                        .stream()
                        .map(ValidationErrorConverter::convert)
                        .collect(Collectors.toList()))
                .build();
    }

    private static Violation convert(ConstraintViolation<?> violation) {
        return Violation
                .builder()
                .fieldName(violation.getPropertyPath().toString())
                .message(violation.getMessage())
                .build();
    }
}
