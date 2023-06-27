package com.security.security;


import com.security.security.config.SecurityConfig;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JPAAuthenticationProvider implements AuthenticationProvider {

    private final JwtUserDetailService userDetailsService;

    private final Md5EncoderDecoder md5EncoderDecoder;

    public JPAAuthenticationProvider(JwtUserDetailService userDetailsService, Md5EncoderDecoder md5EncoderDecoder) {
        this.userDetailsService = userDetailsService;
        this.md5EncoderDecoder = md5EncoderDecoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String phoneNumber = null;

        try {
            phoneNumber = ((JwtUserDetails) authentication.getPrincipal()).getUsername();
        }catch (Exception e){

            phoneNumber = String.valueOf(authentication.getPrincipal());

        }




        UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);

        String userGivenPassword = (String) authentication.getCredentials();
        if (md5EncoderDecoder.matchesText(userGivenPassword,userDetails.getPassword()) || userGivenPassword.equals(userDetails.getPassword())) {
            return UsernamePasswordAuthenticationToken.authenticated(authentication.getPrincipal(), null, userDetails.getAuthorities());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class == authentication;
    }
}
