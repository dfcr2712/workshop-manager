package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskPartNotFoundException extends RuntimeException
{
    public TaskPartNotFoundException(Long taskPartId) {
        super("TaskPart with id " + taskPartId + " not found.");
    }
}
