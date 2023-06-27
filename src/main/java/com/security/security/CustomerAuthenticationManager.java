package com.security.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomerAuthenticationManager implements AuthenticationManager {

    private final DefaultPasswordAuthenticationProvider defaultPasswordAuthenticationProvider;
    private final JPAAuthenticationProvider jpaAuthenticationProvider;

    public CustomerAuthenticationManager(DefaultPasswordAuthenticationProvider defaultPasswordAuthenticationProvider, JPAAuthenticationProvider jpaAuthenticationProvider) {
        this.defaultPasswordAuthenticationProvider = defaultPasswordAuthenticationProvider;
        this.jpaAuthenticationProvider = jpaAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticate = defaultPasswordAuthenticationProvider.authenticate(authentication);

        if (authenticate != null) {
            return authenticate;
        }

        Authentication jpaAuthenticated = jpaAuthenticationProvider.authenticate(authentication);

        if (jpaAuthenticated == null) {
            throw new BadCredentialsException("Unable to authenticate user");
        }

        return jpaAuthenticated;

    }
}
