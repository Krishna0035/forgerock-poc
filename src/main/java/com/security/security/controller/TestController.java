package com.security.security.controller;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    @SecurityRequirement(name = "bearer-key")
    public String test(@RequestHeader("token") String token,@RequestHeader("username") String username){
        return "Hello User";
    }


    @GetMapping("/admin")
    @SecurityRequirement(name = "bearer-key")
    public String admintest(@RequestHeader("token") String token,@RequestHeader("username") String username){
        return "Hello Admin";
    }
}
