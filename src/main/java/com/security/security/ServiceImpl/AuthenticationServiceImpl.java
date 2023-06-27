package com.security.security.ServiceImpl;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.dto.request.input;
import com.security.security.dto.request.user;

import com.security.security.dto.response.ResponseDto;
import com.security.security.service.AuthenticationService;
import com.security.security.util.ApiCallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ApiCallUtil apiCallUtil;


    @Override
    public ResponseDto login(LoginRequestDto requestDto) {
        return loginService.login(requestDto);
    }

    @Override
    public String register(user registerRequestDto) {
        input requestBody = input.builder().user(registerRequestDto).build();
        String uri = "http://test.narayanatutorial.com:8991/am/json/realms/root/realms/test/selfservice/userRegistration?_action=submitRequirements";


        ResponseEntity<String> stringResponseEntity = apiCallUtil.callAPI(HttpMethod.POST, null, requestBody, uri, null);
        System.out.println("stringResponseEntity " + stringResponseEntity);
        return null;
    }

    @Override
    public String logout() {
        return null;
    }
}
