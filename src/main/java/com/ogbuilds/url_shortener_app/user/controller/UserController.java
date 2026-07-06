package com.ogbuilds.url_shortener_app.user.controller;

import com.ogbuilds.url_shortener_app.user.dto.LoginRequest;
import com.ogbuilds.url_shortener_app.user.dto.LoginResponse;
import com.ogbuilds.url_shortener_app.user.dto.RegisterUserRequest;
import com.ogbuilds.url_shortener_app.user.dto.UserResponse;
import com.ogbuilds.url_shortener_app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterUserRequest request)
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/test")
    public String hello()
    {
        return "Hello my friend, yyou are authenticated!";
    }

}
