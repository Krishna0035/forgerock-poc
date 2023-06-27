package com.security.security.ServiceImpl;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.dto.request.user;

import com.security.security.dto.response.ResponseDto;
import com.security.security.service.AuthenticationService;
import com.security.security.util.ApiCallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LogoutService logoutService;

    @Autowired
    private RegisterService registerService;


    @Override
    public ResponseDto login(LoginRequestDto requestDto) {
        return loginService.login(requestDto);
    }

    @Override

    public ResponseDto adminLogin(LoginRequestDto requestDto) {
        return loginService.adminLogin(requestDto);
    }

    @Override
    public ResponseDto register(user registerRequestDto) {

        return registerService.register(registerRequestDto);
    }

    @Override
    public ResponseDto logout(String token) {
//        System.out.println("yes");
//
//         String uri = "http://test.narayanatutorial.com:8991/am/json/sessions?_action=logout";
//
//        Map<String, List<String>> requestToken = new HashMap<>();
//        requestToken.put("iPlanetDirectoryPro",List.of(token));
//
//        ResponseEntity<String> stringResponseEntity = apiCallUtil.callAPI(HttpMethod.POST,requestToken, null, uri, null);

        return logoutService.logout(token);

    }
}
