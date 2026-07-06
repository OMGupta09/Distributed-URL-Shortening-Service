package com.ogbuilds.url_shortener_app.common.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ValidationErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final Map<String, String> errors;

    public ValidationErrorResponse(
            int status,
            String message,
            Map<String, String> errors
    ) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}