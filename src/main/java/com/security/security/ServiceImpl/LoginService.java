package com.security.security.ServiceImpl;

import com.security.security.JwtUserDetailService;
import com.security.security.JwtUtils;
import com.security.security.dto.request.LoginRequestDto;
import com.security.security.exception.ServiceException;
import com.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public String login(LoginRequestDto request){


        UserDetails userDetails = getUserDetails(request.getEmail());


        return getJwtToken(userDetails,request);
    }

    private UserDetails getUserDetails(String email){
      return   userDetailService.loadUserByUsername(email);
    }


    private String getJwtToken(UserDetails userDetails , LoginRequestDto request){


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails,request.getPassword()));
        }catch (DisabledException e){
            throw new ServiceException("User Disabled", HttpStatus.UNAUTHORIZED);
        }catch (BadCredentialsException e){
            throw new ServiceException("Invalid Credential",HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            throw new ServiceException("Invalid Credential",HttpStatus.UNAUTHORIZED);
        }


        Map<String ,Object> claims = new HashMap<>();

        claims.put("channel",request.getChannel());
        claims.put("status",userDetails.isEnabled());


        String token = jwtUtils.generateToken(userDetails,claims);




        return token;
    }
}