package com.ogbuilds.url_shortener_app.url.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUrlRequest {

    @NotBlank(message = "Original URL is required.")
    private String originalUrl;
}