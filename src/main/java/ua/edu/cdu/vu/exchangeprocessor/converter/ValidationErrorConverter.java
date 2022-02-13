package ua.edu.cdu.vu.exchangeprocessor.converter;

import lombok.experimental.UtilityClass;
import ua.edu.cdu.vu.exchangeprocessor.dto.advice.ValidationErrorResponse;
import ua.edu.cdu.vu.exchangeprocessor.dto.advice.Violation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@UtilityClass
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

    private Violation convert(ConstraintViolation<?> violation) {
        return Violation
                .builder()
                .fieldName(violation.getPropertyPath().toString())
                .message(violation.getMessage())
                .build();
    }
}
