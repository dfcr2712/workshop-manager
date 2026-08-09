package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class VehicleNotFoundExceptionByPlate extends RuntimeException {
    public VehicleNotFoundExceptionByPlate(String licensePlate) {
        super("Vehicle with license plate " + licensePlate + " not found");
    }
}
