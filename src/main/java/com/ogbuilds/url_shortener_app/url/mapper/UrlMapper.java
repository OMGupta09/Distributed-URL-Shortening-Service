package com.ogbuilds.url_shortener_app.url.mapper;

import com.ogbuilds.url_shortener_app.url.dto.CreateShortUrlRequest;
import com.ogbuilds.url_shortener_app.url.dto.UrlAnalyticsResponse;
import com.ogbuilds.url_shortener_app.url.dto.UrlResponse;
import com.ogbuilds.url_shortener_app.url.entity.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    public Url toEntity(CreateShortUrlRequest request)
    {
        Url url=new Url();

        url.setOriginalUrl(request.getOriginalUrl());
        url.setExpiresAt(request.getExpiresAt());

        return url;
    }

    public UrlResponse toUrlResponse(Url url)
    {
        return new UrlResponse(
                url.getId(),
                url.getOriginalUrl(),
                url.getShortCode(),
                "http://localhost:8080/urls/" + url.getShortCode(),
                url.getCreatedAt()
        );
    }

    public UrlAnalyticsResponse toAnalyticsResponse(Url url) {

        return new UrlAnalyticsResponse(
                url.getId(),
                url.getShortCode(),
                url.getOriginalUrl(),
                url.getClickCount(),
                url.getLastAccessedAt(),
                url.getCreatedAt()
        );
    }
}
