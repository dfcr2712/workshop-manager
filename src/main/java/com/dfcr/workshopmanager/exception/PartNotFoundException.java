package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class PartNotFoundException extends RuntimeException{

    public PartNotFoundException(Long id){
        super("Part with id " + id + " not found.");
    }

}
