package com.security.security.ServiceImpl;


import com.security.security.dto.request.input;
import com.security.security.dto.request.user;
import com.security.security.dto.response.ResponseDto;
import com.security.security.externalservice.ForgerockService;
import com.security.security.util.ApiCallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RegisterService {


    @Autowired
    private ForgerockService forgerockService;


    public ResponseDto register(user registerRequestDto) {
        return forgerockService.register(registerRequestDto);
    }
}
