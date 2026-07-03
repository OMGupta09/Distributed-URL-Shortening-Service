package com.ogbuilds.url_shortener_app.user.service;

import com.ogbuilds.url_shortener_app.user.dto.LoginRequest;
import com.ogbuilds.url_shortener_app.user.dto.LoginResponse;
import com.ogbuilds.url_shortener_app.user.dto.RegisterUserRequest;
import com.ogbuilds.url_shortener_app.user.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponse register(RegisterUserRequest request);

    LoginResponse login(LoginRequest request);

}
