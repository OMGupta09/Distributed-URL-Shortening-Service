package com.ogbuilds.url_shortener_app.url.service;

import com.ogbuilds.url_shortener_app.url.dto.*;

import java.util.List;

public interface UrlService {

    ShortUrlResponse createShortUrl(CreateShortUrlRequest request);

    String getOriginalUrl(String shortCode);

    List<UrlResponse> getMyUrls();

    void deleteUrl(Long id);

    UrlResponse getUrl(Long id);

    UrlResponse updateUrl(Long id, UpdateUrlRequest request);

    UrlAnalyticsResponse getAnalytics(Long id);
}
