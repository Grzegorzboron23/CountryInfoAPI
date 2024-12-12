package com.volvo.CountryInfoAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CountryServiceException extends RuntimeException {

    public CountryServiceException(String message) {
        super(message);
    }
}
