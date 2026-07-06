package com.ogbuilds.url_shortener_app.url.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UrlResponse {

    private Long id;

    private String originalUrl;

    private String shortCode;

    private String shortUrl;

    private LocalDateTime createdAt;
}