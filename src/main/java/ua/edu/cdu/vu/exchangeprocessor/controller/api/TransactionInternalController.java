package ua.edu.cdu.vu.exchangeprocessor.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.cdu.vu.exchangeprocessor.configuration.security.recaptcha.CaptchaService;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.TransactionType;
import ua.edu.cdu.vu.exchangeprocessor.service.ProfileService;
import ua.edu.cdu.vu.exchangeprocessor.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping("/internal/transactions")
@RequiredArgsConstructor
public class TransactionInternalController {

    private final ProfileService profileService;
    private final TransactionService service;
    private final CaptchaService captchaService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void createTransaction(@RequestParam("quantity") @Min(1) @Max(1000) Integer quantity,
                                  @RequestParam("type") TransactionType transactionType,
                                  HttpServletRequest request) {
        log.info("Requested creation of {} transaction with quantity: {}... by user: {}", transactionType, quantity,
                profileService.getUserId());
        captchaService.processResponse(request);
        service.createTransaction(quantity, transactionType);
    }
}
