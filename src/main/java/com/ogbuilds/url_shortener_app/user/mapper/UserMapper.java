package com.ogbuilds.url_shortener_app.user.mapper;

import com.ogbuilds.url_shortener_app.user.dto.RegisterUserRequest;
import com.ogbuilds.url_shortener_app.user.dto.UserResponse;
import com.ogbuilds.url_shortener_app.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterUserRequest request)
    {
        User user=new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }

    public UserResponse toResponse(User user)
    {
        return new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

}
