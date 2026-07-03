package com.ogbuilds.url_shortener_app.user.service;

import com.ogbuilds.url_shortener_app.user.dto.LoginRequest;
import com.ogbuilds.url_shortener_app.user.dto.LoginResponse;
import com.ogbuilds.url_shortener_app.user.dto.RegisterUserRequest;
import com.ogbuilds.url_shortener_app.user.dto.UserResponse;
import com.ogbuilds.url_shortener_app.user.entity.Role;
import com.ogbuilds.url_shortener_app.user.entity.User;
import com.ogbuilds.url_shortener_app.user.exception.EmailAlreadyExistsException;
import com.ogbuilds.url_shortener_app.user.mapper.UserMapper;
import com.ogbuilds.url_shortener_app.user.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRespository userRespository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterUserRequest request) {

        if(userRespository.existsByUsername(request.getUsername()))
        {
            throw new UsernameNotFoundException("Username already exists!");
        }

        if(userRespository.existsByEmail(request.getEmail()))
        {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);

        userRespository.save(user);

        return userMapper.toResponse(user);

    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRespository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );

    }
}
