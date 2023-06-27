package com.security.security.service;

import com.security.security.dto.request.LoginRequestDto;

public interface AuthenticationService {
    String login(LoginRequestDto requestDto);

    String logout();

}
