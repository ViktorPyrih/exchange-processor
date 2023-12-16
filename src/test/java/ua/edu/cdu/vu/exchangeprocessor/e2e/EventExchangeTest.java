package ua.edu.cdu.vu.exchangeprocessor.e2e;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventExchangeTest {

    private static final String URL = "http://localhost:8080/exchangeView";

    @Test
    void shouldBlockExchangeTransaction_whenUserIsBot() {
        open(URL);
        switchTo().frame($("#transactionForm > div.g-recaptcha.col-sm-5 > div > div > iframe"));
        $("#recaptcha-anchor").click();

        var reCaptchaModal = $("body > div:nth-child(4) > div:nth-child(4) > iframe");

        assertNotNull(reCaptchaModal);
    }
}
