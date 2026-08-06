package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ServiceOrderClosedException extends RuntimeException {
    public ServiceOrderClosedException(Long id) {
        super("Service order with id " + id + " is already closed.");
    }
}
