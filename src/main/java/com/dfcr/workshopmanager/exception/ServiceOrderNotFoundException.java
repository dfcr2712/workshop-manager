package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class ServiceOrderNotFoundException extends RuntimeException {
    public ServiceOrderNotFoundException(Long id) {
        super("Service Order with id " + id + " not found");
    }

}
