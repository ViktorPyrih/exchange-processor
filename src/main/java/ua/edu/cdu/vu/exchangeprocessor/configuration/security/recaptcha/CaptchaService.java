package ua.edu.cdu.vu.exchangeprocessor.configuration.security.recaptcha;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public abstract class CaptchaService {

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    public abstract void processResponse(HttpServletRequest request);

    protected boolean responseSanityCheck(String response) {
        return RESPONSE_PATTERN.matcher(response).matches();
    }
}
