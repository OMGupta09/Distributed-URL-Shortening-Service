package com.ogbuilds.url_shortener_app.url.service;

import com.ogbuilds.url_shortener_app.url.dto.CreateShortUrlRequest;
import com.ogbuilds.url_shortener_app.url.dto.ShortUrlResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UrlService {
    ShortUrlResponse createShortUrl(CreateShortUrlRequest request);

    public String getOriginalUrl(String shortCode);

}
