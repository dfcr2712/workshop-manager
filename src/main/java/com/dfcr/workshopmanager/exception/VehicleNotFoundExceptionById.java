package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class VehicleNotFoundExceptionById extends RuntimeException {
    public VehicleNotFoundExceptionById(Long id) {
        super("Vehicle with id " + id + " not found");
    }
}
