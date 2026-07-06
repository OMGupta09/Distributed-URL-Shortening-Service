package com.ogbuilds.url_shortener_app.url.service;

import com.ogbuilds.url_shortener_app.url.dto.CreateShortUrlRequest;
import com.ogbuilds.url_shortener_app.url.dto.ShortUrlResponse;
import com.ogbuilds.url_shortener_app.url.entity.Url;
import com.ogbuilds.url_shortener_app.url.mapper.UrlMapper;
import com.ogbuilds.url_shortener_app.url.repository.UrlRepository;
import com.ogbuilds.url_shortener_app.url.util.ShortCodeGenerator;
import com.ogbuilds.url_shortener_app.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;

    @Override
    public ShortUrlResponse createShortUrl(CreateShortUrlRequest request) {

        Url url = urlMapper.toEntity(request);

        String shortCode;

        do{
            shortCode = ShortCodeGenerator.generate();
        }while(urlRepository.existsByShortCode(shortCode));

        url.setShortCode(shortCode);

        url.setOwner(getCurrentUser());

        urlRepository.save(url);

        return new ShortUrlResponse(
                url.getOriginalUrl(),
                url.getShortCode(),
                "http://localhost:8080/urls/" + url.getShortCode());

    }

    @Override
    public String getOriginalUrl(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new RuntimeException("Short URL not found"));

        return url.getOriginalUrl();
    }

    private User getCurrentUser()
    {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }
}
