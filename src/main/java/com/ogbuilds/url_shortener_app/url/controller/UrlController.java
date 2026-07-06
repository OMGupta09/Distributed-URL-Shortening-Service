package com.ogbuilds.url_shortener_app.url.controller;

import com.ogbuilds.url_shortener_app.url.dto.CreateShortUrlRequest;
import com.ogbuilds.url_shortener_app.url.dto.ShortUrlResponse;
import com.ogbuilds.url_shortener_app.url.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;


    @PostMapping
    public ShortUrlResponse createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request
            )
    {
        return urlService.createShortUrl(request);
    }

    @GetMapping("/{shortCode}")
    public void redirect(
            @PathVariable("shortCode") String shortCode,
            HttpServletResponse response
    ) throws IOException {
        response.sendRedirect(urlService.getOriginalUrl(shortCode));
    }

}
