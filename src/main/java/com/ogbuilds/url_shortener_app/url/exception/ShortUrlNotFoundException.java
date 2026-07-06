package com.ogbuilds.url_shortener_app.url.exception;

public class ShortUrlNotFoundException extends RuntimeException{

    public ShortUrlNotFoundException(String message)
    {
        super(message);
    }


}
