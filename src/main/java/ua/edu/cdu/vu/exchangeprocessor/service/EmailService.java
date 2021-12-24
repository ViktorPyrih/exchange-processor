package ua.edu.cdu.vu.exchangeprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.edu.cdu.vu.exchangeprocessor.component.EmailMessageCreator;
import ua.edu.cdu.vu.exchangeprocessor.entity.UserEntity;
import ua.edu.cdu.vu.exchangeprocessor.repository.UserRepository;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${app.mail.visit-memo-days:14}")
    private Integer visitMemoDays;

    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final EmailMessageCreator creator;
    
    public void startSendingUserVisitationMemos() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(visitMemoDays);
        List<UserEntity> inactiveUsers = userRepository.findAllByDateOfLastVisitLessThanEqual(dateTime);
        log.info("Found {} users to send memo to...", inactiveUsers.size());

        MimeMessage[] mailMessages = prepareEmailMessages(inactiveUsers);

        mailSender.send(mailMessages);
    }

    public void sendInvitationEmail(String email) {
        MimeMessage mailMessage = creator.createInvitationEmail(email, profileService.getPrincipalUsername());
        mailSender.send(mailMessage);
    }

    private MimeMessage[] prepareEmailMessages(List<UserEntity> inactiveUsers) {
        return inactiveUsers
                .stream()
                .map(userEntity -> creator.createVisitationMemoEmail(userEntity.getEmail(),
                        userEntity.getName(), userEntity.getDateOfLastVisit()))
                .toArray(MimeMessage[]::new);
    }
}
