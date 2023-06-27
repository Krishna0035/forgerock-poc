package com.security.security.controller;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.dto.response.ResponseDto;
import com.security.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login.jsp";
//    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto requestDto) {
        // Perform authentication logic (e.g., using Spring Security authentication manager)

      return   authenticationService.login(requestDto);

    }
}

