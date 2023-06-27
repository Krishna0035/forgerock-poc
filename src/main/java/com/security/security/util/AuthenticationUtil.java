package com.security.security.util;


import com.security.security.dto.UserToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {

    public UserToken currentLoggedInUser(){
        return (UserToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getPartyIdFromCurrentLoggedInUser(){
        return ((UserToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
