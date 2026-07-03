package com.ogbuilds.url_shortener_app.user.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message)
    {
        super(message);
    }

}
