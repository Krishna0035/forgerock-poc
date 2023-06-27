package com.security.security;

import com.security.security.config.SecurityConfig;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class DefaultPasswordAuthenticationProvider implements AuthenticationProvider {



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (Objects.equals("1234", (String) authentication.getCredentials())) {
            return UsernamePasswordAuthenticationToken
                    .authenticated(authentication.getPrincipal(), null,
                            SecurityConfig.ROLE_USER);
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
