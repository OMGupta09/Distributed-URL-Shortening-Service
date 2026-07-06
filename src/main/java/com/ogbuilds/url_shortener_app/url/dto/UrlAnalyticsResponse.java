package com.ogbuilds.url_shortener_app.url.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UrlAnalyticsResponse {

    private Long id;

    private String shortCode;

    private String originalUrl;

    private Long clickCount;

    private LocalDateTime lastAccessedAt;

    private LocalDateTime createdAt;
}