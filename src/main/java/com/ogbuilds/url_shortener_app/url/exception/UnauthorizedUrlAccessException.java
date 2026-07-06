package com.ogbuilds.url_shortener_app.url.exception;

public class UnauthorizedUrlAccessException extends RuntimeException{

    public UnauthorizedUrlAccessException(String message)
    {
        super(message);
    }

}
