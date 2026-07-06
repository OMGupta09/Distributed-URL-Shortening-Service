package com.ogbuilds.url_shortener_app.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Long id;
    private String username;
    private String email;
    private String token;

    public LoginResponse(Long id, String username, String email,String token) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.token=token;
    }
}
