package ua.edu.cdu.vu.exchangeprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final ProfileService profileService;
    private final EmailService emailService;
    private final ExchangeEventService generationService;

    @Scheduled(cron = "#{schedulingProperties.exchange.cron}", zone = "#{schedulingProperties.exchange.zone}")
    public void scheduleExchangeEventGeneration() {
        log.info("Scheduled exchange event generation...");
        generationService.generateRandomEvent();
    }

    @Scheduled(cron = "#{schedulingProperties.gift.cron}", zone = "#{schedulingProperties.gift.zone}")
    public void scheduleUserGiftSending() {
        log.info("Scheduled gift sending...");
        profileService.updateRecentActiveUsersBalance();
    }

    @Scheduled(cron = "#{schedulingProperties.visitMemo.cron}", zone = "#{schedulingProperties.visitMemo.zone}")
    public void scheduleUserVisitationMemoSending() {
        log.info("Scheduled visitation memo sending...");
        emailService.startSendingUserVisitationMemos();
    }
}
