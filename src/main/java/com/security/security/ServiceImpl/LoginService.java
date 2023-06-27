package com.security.security.ServiceImpl;

import com.security.security.dto.request.LoginRequestDto;
import com.security.security.dto.response.ResponseDto;
import com.security.security.externalservice.ForgerockService;
import com.security.security.externalservice.dto.ForgerockLoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoginService {


//
//    @Autowired
//    private AuthenticationManager authenticationManager;



    @Autowired
    private ForgerockService forgerockService;

    public ResponseDto login(LoginRequestDto request){


//        UserDetails userDetails = getUserDetails(request.getEmail());


        return getToken(request);
    }


    public ResponseDto adminLogin(LoginRequestDto request){


//        UserDetails userDetails = getUserDetails(request.getEmail());


        return getTokenForAdmin(request);
    }




    private ResponseDto getToken( LoginRequestDto request){


        ForgerockLoginRequestDto forgerockLoginRequestDto = ForgerockLoginRequestDto.builder()
                .username(request.getEmail())
                .password(request.getPassword())
                .build();

        return forgerockService.login(forgerockLoginRequestDto);
    }


    private ResponseDto getTokenForAdmin( LoginRequestDto request){


        ForgerockLoginRequestDto forgerockLoginRequestDto = ForgerockLoginRequestDto.builder()
                .username(request.getEmail())
                .password(request.getPassword())
                .build();

        return forgerockService.loginAdmin(forgerockLoginRequestDto);
    }
}
