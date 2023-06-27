package com.security.security.controller;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login.jsp";
    }

    @PostMapping("/loginnn")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // Perform authentication logic (e.g., using Spring Security authentication manager)

        LoginRequestDto loginRequestDto = LoginRequestDto.builder().email(username).channel("web").password(password).build();
        authenticationService.login(loginRequestDto);
        // Redirect the admin user to the Swagger UI if authentication is successful
        return "redirect:/swagger-ui/index.html";
    }
}

