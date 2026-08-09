package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super("Vehicle with id " + id + " not found");
    }

    public VehicleNotFoundException(String licensePlate) {
        super("Vehicle with license plate " + licensePlate + " not found");
    }
}
