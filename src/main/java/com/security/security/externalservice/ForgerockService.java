package com.security.security.externalservice;

import com.security.security.dto.response.ResponseDto;
import com.security.security.externalservice.dto.ForgerockLoginRequestDto;

public interface ForgerockService {

    ResponseDto login(ForgerockLoginRequestDto request);

    ResponseDto getUserDetails(String token);

}
