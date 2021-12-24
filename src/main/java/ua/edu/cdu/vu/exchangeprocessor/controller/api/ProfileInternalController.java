package ua.edu.cdu.vu.exchangeprocessor.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.cdu.vu.exchangeprocessor.client.GoogleApiClient;
import ua.edu.cdu.vu.exchangeprocessor.service.ProfileService;

import java.util.List;

@Slf4j
@RequestMapping("/internal/profile")
@RestController
@RequiredArgsConstructor
public class ProfileInternalController {

    private final ProfileService profileService;
    private final GoogleApiClient googleApiClient;

    @GetMapping("/contactEmails")
    public List<String> getMostSignificantExchangeEvents() {
        log.info("Requested to return contact emails for user with id: {}", profileService.getUserId());
        List<String> emails = googleApiClient.getPrincipalContactsEmails();
        log.info("Found {} contact emails", emails.size());

        return emails;
    }

    @GetMapping("/balance")
    public Integer getPrincipalBalance() {
        log.info("Requested to return balance for user with id: {}", profileService.getUserId());
        return profileService.getPrincipalBalance();
    }
}
