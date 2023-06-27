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
import java.util.Map;

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
    public ResponseEntity<String> register(user registerRequestDto) {
//        input requestBody = input.builder().user(registerRequestDto).build();
        Map<String,Object> requestBody = new HashMap<>();
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("user",registerRequestDto);
        requestBody.put("input",userMap);
        String uri = "http://test.narayanatutorial.com:8991/am/json/realms/root/realms/test/selfservice/userRegistration?_action=submitRequirements";

        System.out.println(requestBody);
        ResponseEntity<String> stringResponseEntity = apiCallUtil.callAPI(HttpMethod.POST, null, requestBody, uri, null);
        System.out.println("stringResponseEntity " + stringResponseEntity);
        return stringResponseEntity;
    }

    @Override
    public String logout() {
        return null;
    }
}
