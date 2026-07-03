package com.ogbuilds.url_shortener_app.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Long id;
    private String username;
    private String email;

    public LoginResponse(Long id, String username, String email) {
        this.username = username;
        this.id = id;
        this.email = email;
    }
}
