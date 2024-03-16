package com.wex.purchase.core.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Error implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;

    private HttpStatus code;
    private String message;
}
