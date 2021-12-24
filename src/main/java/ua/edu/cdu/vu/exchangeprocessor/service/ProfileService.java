package ua.edu.cdu.vu.exchangeprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.UserProperties;
import ua.edu.cdu.vu.exchangeprocessor.dto.InternalUser;
import ua.edu.cdu.vu.exchangeprocessor.entity.UserEntity;
import ua.edu.cdu.vu.exchangeprocessor.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProperties userProperties;

    private final ExchangeEventService exchangeEventService;
    private final UserRepository userRepository;

    public InternalUser getUserProfile() {
        return (InternalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getUserId() {
        return getUserProfile().getId();
    }

    public void updateRecentActiveUsersBalance() {
        Integer actualPrice = exchangeEventService.calculateActualPrice();
        userRepository.updateRecentActiveUsersBalance(actualPrice, LocalDateTime.now().minusDays(userProperties.getGiftDays()));
    }

    public Optional<UserEntity> findPrincipalEntity() {
        Long userId = getUserId();
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            logout();
            log.error("Unexpected error occurred. User with id: {} wasn't found", userId);
        }

        return user;
    }

    public String getPrincipalUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Integer getPrincipalBalance() {
        return findPrincipalEntity()
                .map(UserEntity::getBalance)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE));
    }

    private void logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }
}
