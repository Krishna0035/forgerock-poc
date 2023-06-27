package com.security.security.ServiceImpl;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private LoginService loginService;
    @Override
    public String login(LoginRequestDto requestDto) {
        return loginService.login(requestDto);
    }

    @Override
    public String logout() {
        return null;
    }
}
