package com.security.security.dto.request;

import lombok.Data;

@Data
public class user {
    private String username;
    private String givenName;
    private String sn;
    private String mail;
    private String userPassword;
    private String inetUserStatus;
}
