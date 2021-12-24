package ua.edu.cdu.vu.exchangeprocessor.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import ua.edu.cdu.vu.exchangeprocessor.entity.UserEntity;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    public static UserEntity dtoToEntity(OidcUser userDto) {
        return UserEntity
                .builder()
                .dateOfLastVisit(LocalDateTime.now())
                .name(userDto.getFullName())
                .email(userDto.getEmail())
                .identifier(userDto.getName())
                .build();
    }
}
