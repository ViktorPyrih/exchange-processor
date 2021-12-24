package ua.edu.cdu.vu.exchangeprocessor.dto.recaptcha;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleResponse {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("error-codes")
    private Set<ErrorCode> errorCodes = new HashSet<>();

    @JsonIgnore
    public boolean hasClientErrors() {
        return errorCodes.contains(ErrorCode.MISSING_RESPONSE) || errorCodes.contains(ErrorCode.INVALID_RESPONSE);
    }
}
