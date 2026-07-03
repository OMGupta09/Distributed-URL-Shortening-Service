package com.ogbuilds.url_shortener_app.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest {

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8,message = "Password should contain minimum 8 characters")
    private String password;

}
