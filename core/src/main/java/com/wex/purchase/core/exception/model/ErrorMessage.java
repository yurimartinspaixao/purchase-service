package com.wex.purchase.core.exception.model;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    NO_RATE_AVAILABLE_CODE(HttpStatus.NOT_FOUND, "The purchase cannot be converted to the target currency."),
    API_REQUEST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "The api request call has failed");

    private final HttpStatus code;
    private final String message;

    ErrorMessage(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}