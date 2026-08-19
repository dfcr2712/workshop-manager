package com.dfcr.workshopmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(HttpStatus.CONFLICT)

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(BigDecimal quantity) {
        super("Insufficient stock for quantity: " + quantity);
    }
}
