package com.security.security.ServiceImpl;


import com.security.security.dto.response.ResponseDto;
import com.security.security.externalservice.ForgerockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogoutService {

    @Autowired
    private ForgerockService forgerockService;


    public ResponseDto logout(String token){
       return forgerockService.logout(token);
    }
}
