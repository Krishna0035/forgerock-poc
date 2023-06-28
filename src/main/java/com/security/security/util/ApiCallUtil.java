package com.security.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.security.security.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ApiCallUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${active}")
    private int active;

    @Value("${not.active}")
    private int notActive;

    @Value("${success}")
    private int success;

    @Value("${fails}")
    private int fail;


    public ResponseEntity<String> callAPI(HttpMethod httpMethod, Map<String, List<String>> headers, Object body, String url,String type)
    {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(!Objects.isNull(headers))
        {
            for(Map.Entry<String, List<String>> entry: headers.entrySet())
            {
                httpHeaders.put(entry.getKey(), entry.getValue());
            }
        }
        URI uri = null;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestStr = null;
        try {
            requestStr = ow.writeValueAsString(body);
            System.out.println("request: "+requestStr);
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("message", "URL is invalid");
            throw new ServiceException(map);
        }

        RequestEntity<Object> request = new RequestEntity<>(body, httpHeaders, httpMethod, uri);
        LocalDateTime requestedAt = LocalDateTime.now(ZoneId.of("UTC"));
        ResponseEntity<String> responseEntity = null;

        try
        {
            responseEntity = restTemplate.exchange(uri, httpMethod, request, String.class);

        }catch(HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
        catch(Exception e)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            throw new ServiceException(map);
        }

        return responseEntity;
    }

}
