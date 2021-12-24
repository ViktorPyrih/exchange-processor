package ua.edu.cdu.vu.exchangeprocessor.component;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.email.EmailProperties;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailMessageCreator {

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    private final EmailProperties emailProperties;
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;


    public MimeMessage createVisitationMemoEmail(String email, String username, LocalDateTime dateTimeOfLastVisit) {
        Map<String, Object> args = Map.of("username", username, "dateOfLastVisit", dateTimeOfLastVisit.toLocalDate());
        String template = generateTemplate(emailProperties.getVisitationMemo().getTemplate(), args);

        return createMimeMessage(email, emailProperties.getVisitationMemo().getSubject(), template);
    }

    public MimeMessage createInvitationEmail(String email, String username) {
        Map<String, Object> args = Map.of("username", username);
        String template = generateTemplate(emailProperties.getInvitation().getTemplate(), args);

        return createMimeMessage(email, emailProperties.getInvitation().getSubject(), template);
    }

    @SuppressWarnings("all")
    private String generateTemplate(String viewName, Map<String, Object> args) {
        Context context = new Context();
        args.forEach(context::setVariable);
        String template = templateEngine.process(viewName, context);

        return template;
    }

    @SneakyThrows
    private MimeMessage createMimeMessage(String to, String subject, String template) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        mimeMessage.setContent(template, CONTENT_TYPE);

        helper.setTo(to);
        helper.setSubject(subject);

        return mimeMessage;
    }
}
