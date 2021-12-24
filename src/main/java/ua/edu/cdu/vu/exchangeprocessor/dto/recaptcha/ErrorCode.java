package ua.edu.cdu.vu.exchangeprocessor.dto.recaptcha;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {

    MISSING_SECRET("missing-input-secret"),
    INVALID_SECRET("invalid-input-secret"),
    MISSING_RESPONSE("missing-input-response"),
    INVALID_RESPONSE("invalid-input-response");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
