package ua.edu.cdu.vu.exchangeprocessor.configuration.security.recaptcha;

import com.google.common.cache.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ReCaptchaAttemptService {

    private static final int MAX_ATTEMPT = 4;
    private final LoadingCache<String, Integer> attemptsCache;

    @SuppressWarnings("all")
    public ReCaptchaAttemptService() {
        this.attemptsCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(MAX_ATTEMPT, TimeUnit.HOURS)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void recaptchaSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void recaptchaFailed(String key) {
        int attempts = attemptsCache.getUnchecked(key);
        attemptsCache.put(key, ++attempts);
    }

    public boolean isBlocked(String key) {
        return attemptsCache.getUnchecked(key) >= MAX_ATTEMPT;
    }
}
