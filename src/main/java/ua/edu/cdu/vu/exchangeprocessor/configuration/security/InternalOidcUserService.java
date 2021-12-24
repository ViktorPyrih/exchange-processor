package ua.edu.cdu.vu.exchangeprocessor.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ua.edu.cdu.vu.exchangeprocessor.configuration.properties.UserProperties;
import ua.edu.cdu.vu.exchangeprocessor.converter.UserConverter;
import ua.edu.cdu.vu.exchangeprocessor.dto.InternalUser;
import ua.edu.cdu.vu.exchangeprocessor.entity.UserEntity;
import ua.edu.cdu.vu.exchangeprocessor.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalOidcUserService extends OidcUserService {

    private final UserProperties userProperties;
    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser user = super.loadUser(userRequest);
        log.debug("User with name {} and id: {} has successfully authenticated", user.getName(), user.getSubject());
        Long id = saveUserLocallyIfNew(user);

        return new InternalUser(id, user);
    }

    private Long saveUserLocallyIfNew(OidcUser user) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByIdentifier(user.getSubject());

        if (userEntity.isEmpty()) {
            log.debug("New user with name: {} and id: {} detected and saved to the data base", user.getName(), user.getSubject());
            UserEntity newUser = UserConverter.dtoToEntity(user);
            BeanUtils.copyProperties(userProperties, newUser);
            return userRepository.save(newUser).getId();
        }

        return userEntity.get().getId();
    }
}
