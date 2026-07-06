package com.ogbuilds.url_shortener_app.url.exception;

public class UrlExpiredException extends RuntimeException{

    public UrlExpiredException(String message)
    {
        super(message);
    }

}
