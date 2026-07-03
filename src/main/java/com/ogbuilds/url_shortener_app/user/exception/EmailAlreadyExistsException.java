package com.ogbuilds.url_shortener_app.user.exception;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String message)
    {
        super(message);
    }

}
