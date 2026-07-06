package com.ogbuilds.url_shortener_app.user.service;

import com.ogbuilds.url_shortener_app.user.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRespository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User Not Found"));
    }
}
