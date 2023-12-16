package ua.edu.cdu.vu.exchangeprocessor.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OidcUserService oidcUserService;

    @Value("${spring.security.enabled}")
    private boolean enabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (enabled) {
            http
                    .csrf().ignoringAntMatchers("/internal/transactions/create", "/internal/email/send")
                    .and()
                    .oauth2Login()
                    .userInfoEndpoint().oidcUserService(oidcUserService)
                    .and()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/home")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/webjars/**", "/home", "/error", "/favicon/**", "/js/*", "/css/*", "/img/*")
                    .permitAll()
                    .anyRequest().authenticated();
        }
    }
}
