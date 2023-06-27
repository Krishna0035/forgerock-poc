package com.security.security.service;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.dto.request.user;
import com.security.security.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseDto login(LoginRequestDto requestDto);
    ResponseEntity<String> register(user registerRequestDto);


    String logout();

}
