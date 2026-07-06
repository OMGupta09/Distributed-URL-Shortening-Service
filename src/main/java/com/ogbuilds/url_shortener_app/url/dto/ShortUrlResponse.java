package com.ogbuilds.url_shortener_app.url.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortUrlResponse {

    private final String originalUrl;

    private final String shortCode;

    private final String shortUrl;

}
