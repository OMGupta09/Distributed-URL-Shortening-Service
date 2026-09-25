package com.ogbuilds.url_shortener_app.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private String username;
    private String email;

    public UserResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }
}