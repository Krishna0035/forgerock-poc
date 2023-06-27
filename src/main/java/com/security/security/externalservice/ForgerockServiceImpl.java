package com.security.security.externalservice;

import com.google.gson.Gson;
import com.security.security.dto.request.input;
import com.security.security.dto.request.user;
import com.security.security.dto.response.ResponseDto;
import com.security.security.externalservice.dto.ForgerockLoginRequestDto;
import com.security.security.externalservice.dto.LoginSuccessResponseDto;
import com.security.security.util.ApiCallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForgerockServiceImpl implements ForgerockService{

    @Autowired
    private ApiCallUtil apiCallUtil;

    @Value("${realmName}")
    private String realmName;

    @Value("${domainName}")
    private String domainName;

    @Value("${port}")
    private String port;

    @Value("${customRealmPath}")
    private String realmPath;

    @Value("${basePath}")
    private String basePath;

    @Autowired
    private Gson gson;

    @Override
    public ResponseDto login(ForgerockLoginRequestDto request) {


        String url = "http://"+realmName+"."+domainName+":"+port+basePath+realmPath+"/authenticate";


        Map<String, List<String>> headers = new HashMap<>();

//        headers.put("Content-Type",List.of("application/json"));
        headers.put("X-OpenAM-Username",List.of(request.getUsername()));
        headers.put("X-OpenAM-Password",List.of(request.getPassword()));
        headers.put("Accept-API-Version",List.of("resource=2.0, protocol=1.0"));


        ResponseEntity<String> response = apiCallUtil.callAPI(HttpMethod.POST, headers, null, url, null);

        LoginSuccessResponseDto data = gson.fromJson(response.getBody(), LoginSuccessResponseDto.class);

      ResponseDto responseDto =  ResponseDto.builder()
                .status(true)
                .message("Successful")
                .data(data)
                .build();


        return responseDto;
    }

    @Override
    public ResponseDto loginAdmin(ForgerockLoginRequestDto request) {

//        String url = "http://openam.narayanatutorial.com:8991/am/json/realms/root/realms/test/authenticate";

        String url = "http://"+realmName+"."+domainName+":"+port+basePath+"/authenticate";


        Map<String, List<String>> headers = new HashMap<>();

//        headers.put("Content-Type",List.of("application/json"));
        headers.put("X-OpenAM-Username",List.of(request.getUsername()));
        headers.put("X-OpenAM-Password",List.of(request.getPassword()));
        headers.put("Accept-API-Version",List.of("resource=2.0, protocol=1.0"));


        ResponseEntity<String> response = apiCallUtil.callAPI(HttpMethod.POST, headers, null, url, null);

        LoginSuccessResponseDto data = gson.fromJson(response.getBody(), LoginSuccessResponseDto.class);

        ResponseDto responseDto =  ResponseDto.builder()
                .status(true)
                .message("Successful")
                .data(data)
                .build();


        return responseDto;
    }

    @Override
    public ResponseDto getUserDetails(String token,String username) {
        String url = "http://"+realmName+"."+domainName+":"+port+basePath+realmPath+"/users/"+username;


        Map<String, List<String>> headers = new HashMap<>();

//        headers.put("Content-Type",List.of("application/json"));
        headers.put("iPlanetDirectoryPro",List.of(token));
        headers.put("Accept-API-Version",List.of("resource=2.0, protocol=1.0"));


        ResponseEntity<String> response = apiCallUtil.callAPI(HttpMethod.GET, headers, null, url, null);

        ResponseDto responseDto =  ResponseDto.builder()
                .status(true)
                .message("Successful")
                .data(response.getBody())
                .build();



        return responseDto;
    }

    @Override
    public ResponseDto getAdminDetails(String token, String username) {
        String url = "http://"+realmName+"."+domainName+":"+port+basePath+"/users/"+username;


        Map<String, List<String>> headers = new HashMap<>();

//        headers.put("Content-Type",List.of("application/json"));
        headers.put("iPlanetDirectoryPro",List.of(token));
        headers.put("Accept-API-Version",List.of("resource=2.0, protocol=1.0"));


        ResponseEntity<String> response = apiCallUtil.callAPI(HttpMethod.GET, headers, null, url, null);

        ResponseDto responseDto =  ResponseDto.builder()
                .status(true)
                .message("Successful")
                .data(response.getBody())
                .build();



        return responseDto;
    }

    @Override
    public ResponseDto register(user registerRequestDto) {


//        String uri = "http://test.narayanatutorial.com:8991/am/json/realms/root/realms/test/selfservice/userRegistration?_action=submitRequirements";

        String url = "http://"+realmName+"."+domainName+":"+port+basePath+realmPath+"/selfservice/userRegistration?_action=submitRequirements";

        Map<String,Object> requestBody = new HashMap<>();
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("user",registerRequestDto);
        requestBody.put("input",userMap);

        System.out.println(requestBody);
        ResponseEntity<String> stringResponseEntity = apiCallUtil.callAPI(HttpMethod.POST, null, requestBody, url, null);
        System.out.println("stringResponseEntity " + stringResponseEntity);
//        return stringResponseEntity;


        ResponseDto responseDto =  ResponseDto.builder()
                .status(true)
                .message("Successful")
                .data(stringResponseEntity.getBody())
                .build();



        return responseDto;
    }

    @Override
    public ResponseDto logout(String token) {

        System.out.println("yes");

        String uri = "http://test.narayanatutorial.com:8991/am/json/sessions?_action=logout";

        Map<String, List<String>> requestToken = new HashMap<>();
        requestToken.put("iPlanetDirectoryPro",List.of(token));

        ResponseEntity<String> stringResponseEntity = apiCallUtil.callAPI(HttpMethod.POST,requestToken, null, uri, null);

        ResponseDto responseDto =  ResponseDto.builder()
                .status(true)
                .message("Successful")
                .data(stringResponseEntity.getBody())
                .build();



        return responseDto;
    }
}
