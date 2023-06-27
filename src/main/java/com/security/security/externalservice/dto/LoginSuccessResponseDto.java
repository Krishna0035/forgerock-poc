package com.security.security.externalservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginSuccessResponseDto {

    private String tokenId;

    private String successUrl;

    private String realm;

}
