package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskServiceNotFoundException extends RuntimeException {

    public TaskServiceNotFoundException(Long id) {
        super("Task with id " + id + " not found");
    }
}
