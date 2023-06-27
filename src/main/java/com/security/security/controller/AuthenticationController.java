package com.security.security.controller;


import com.security.security.dto.request.LoginRequestDto;
import com.security.security.dto.response.ResponseDto;
import com.security.security.dto.request.user;
import com.security.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto requestDto) {
        // Perform authentication logic (e.g., using Spring Security authentication manager)

        return   authenticationService.login(requestDto);

    }


    @PostMapping("/login/admin")
    public ResponseDto adminLogin(@RequestBody LoginRequestDto requestDto) {
        // Perform authentication logic (e.g., using Spring Security authentication manager)

        return   authenticationService.adminLogin(requestDto);

    }


    @PostMapping("/register")

    public ResponseDto register(@RequestBody user requestDto){
        return authenticationService.register(requestDto);
    }


    @PostMapping("/logout")
    public ResponseDto logout(@RequestHeader("token") String token){
        return authenticationService.logout(token);
    }


}
