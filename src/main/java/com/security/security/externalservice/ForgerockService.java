package com.security.security.externalservice;

import com.security.security.dto.request.user;
import com.security.security.dto.response.ResponseDto;
import com.security.security.externalservice.dto.ForgerockLoginRequestDto;

public interface ForgerockService {

    ResponseDto login(ForgerockLoginRequestDto request);

    ResponseDto loginAdmin(ForgerockLoginRequestDto request);

    ResponseDto getUserDetails(String token,String username);

    ResponseDto getAdminDetails(String token,String username);


    ResponseDto register(user registerRequestDto);

}
