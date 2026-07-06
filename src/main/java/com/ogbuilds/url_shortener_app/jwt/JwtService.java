package com.ogbuilds.url_shortener_app.jwt;

import com.ogbuilds.url_shortener_app.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
