package com.ogbuilds.url_shortener_app.url.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class CreateShortUrlRequest {

    @NotBlank(message = "Original URL is required.")
    @URL(message = "Please provide a valid URL.")
    private String originalUrl;
}