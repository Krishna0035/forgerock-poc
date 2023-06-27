package com.security.security.service;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.dto.request.user;
import com.security.security.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthenticationService {

    ResponseDto login(LoginRequestDto requestDto);


    ResponseDto adminLogin(LoginRequestDto requestDto);
    ResponseDto register(user registerRequestDto);



    ResponseDto logout( String token);

}
