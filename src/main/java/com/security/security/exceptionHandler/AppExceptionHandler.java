package com.security.security.exceptionHandler;

import com.security.security.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class AppExceptionHandler {



        @ExceptionHandler(value = ServiceException.class)
        public ResponseEntity<Object> handleServiceException(ServiceException ex){

            Object erroMessageDescription = ex.getMessage();

            if(!Objects.isNull(ex.getIndexError())){
                erroMessageDescription=(ex.getIndexError());
            }

            return new ResponseEntity<>(erroMessageDescription,new HttpHeaders(),ex.getStatusCode());

        }


}
