package com.security.security.externalservice;

import com.security.security.dto.response.ResponseDto;
import com.security.security.externalservice.dto.ForgerockLoginRequestDto;
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


//    realmName=int
//            domainName = narayanatutorial.com
//    port=8991
//    basePath=/am/json/realms/root
//    customRealmPath= /realms/int

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

    @Override
    public ResponseDto login(ForgerockLoginRequestDto request) {

//        String url = "http://openam.narayanatutorial.com:8991/am/json/realms/root/realms/test/authenticate";

        String url = "http://"+realmName+"."+domainName+":"+port+basePath+realmPath+"/authenticate";


        Map<String, List<String>> headers = new HashMap<>();

//        headers.put("Content-Type",List.of("application/json"));
        headers.put("X-OpenAM-Username",List.of(request.getUsername()));
        headers.put("X-OpenAM-Password",List.of(request.getPassword()));
        headers.put("Accept-API-Version",List.of("resource=2.0, protocol=1.0"));


        ResponseEntity<String> response = apiCallUtil.callAPI(HttpMethod.POST, headers, null, url, null);

      ResponseDto responseDto =  ResponseDto.builder()
                .status(true)
                .message("Successful")
                .data(response.getBody())
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
}
