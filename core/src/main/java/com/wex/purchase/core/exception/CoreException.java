package com.wex.purchase.core.exception;

import com.wex.purchase.core.exception.model.Error;
import org.springframework.http.HttpStatus;

public class CoreException extends RuntimeException {
    private final Error error;

    public CoreException(HttpStatus status, String message) {
        super();
        this.error = new Error(status, message);
    }

    public Error getErrorMessage() {
        return error;
    }
}