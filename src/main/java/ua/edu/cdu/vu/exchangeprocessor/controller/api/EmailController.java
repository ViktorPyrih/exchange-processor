package ua.edu.cdu.vu.exchangeprocessor.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.cdu.vu.exchangeprocessor.service.EmailService;
import ua.edu.cdu.vu.exchangeprocessor.service.ProfileService;

import javax.validation.constraints.Email;

@Slf4j
@Validated
@RequestMapping("/internal/email")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final ProfileService profileService;
    private final EmailService emailService;

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTransaction(@RequestParam("email") @Email String email) {
        log.info("Requested sending invitation email to: {}... by user: {}", email, profileService.getUserId());
        emailService.sendInvitationEmail(email);
    }
}
