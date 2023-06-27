package com.security.security.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class user {
    private String username;
    private String givenName;
    private String sn;
    private String mail;
    private String userPassword;
    private String inetUserStatus;
}
