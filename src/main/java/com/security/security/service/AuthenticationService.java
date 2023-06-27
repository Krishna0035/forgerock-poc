package com.security.security.service;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.dto.response.ResponseDto;

public interface AuthenticationService {
    ResponseDto login(LoginRequestDto requestDto);

    String logout();

}
