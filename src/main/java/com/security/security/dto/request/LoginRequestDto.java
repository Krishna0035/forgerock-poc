package com.security.security.dto.request;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {

    @NonNull
    private String username;

    @NonNull
    private String password;


}
