package ua.edu.cdu.vu.exchangeprocessor.configuration.security.recaptcha;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.google.CaptchaSettings;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.google.GoogleProperties;
import ua.edu.cdu.vu.exchangeprocessor.dto.recaptcha.GoogleResponse;
import ua.edu.cdu.vu.exchangeprocessor.exception.InvalidRecaptchaException;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class GoogleRecaptchaService extends CaptchaService {

    private final ReCaptchaAttemptService attemptService;

    private final RestTemplate restOperations;
    private final CaptchaSettings settings;
    private final GoogleProperties googleProperties;

    @Override
    public void processResponse(HttpServletRequest request) {
        if (attemptService.isBlocked(request.getRemoteAddr())) {
            throw new InvalidRecaptchaException("Client exceeded maximum number of failed attempts");
        }
        String response = request.getParameter(googleProperties.getKey());
        if (!StringUtils.hasText(response)) {
            throw new InvalidRecaptchaException("ReCaptcha is missing");
        }
        if (!responseSanityCheck(response)) {
            throw new InvalidRecaptchaException("Response contains invalid characters");
        }

        validateResponse(response, request.getRemoteAddr());
    }

    private void validateResponse(String response, String clientIp) {
        String url = String.format(googleProperties.getUrl(), settings.getSecret(), response, clientIp);
        try {
            GoogleResponse googleResponse = restOperations.getForObject(url, GoogleResponse.class);
            if (googleResponse == null || !googleResponse.isSuccess()) {
                if (googleResponse != null && googleResponse.hasClientErrors()) {
                    attemptService.recaptchaFailed(clientIp);
                }
                throw new InvalidRecaptchaException("Recaptcha was not successfully validated");
            }
            attemptService.recaptchaSucceeded(clientIp);
        } catch (RestClientException e) {
            throw new InvalidRecaptchaException("Google Recaptcha service can't be reached");
        }
    }
}
