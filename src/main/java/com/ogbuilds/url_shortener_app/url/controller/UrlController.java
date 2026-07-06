package com.ogbuilds.url_shortener_app.url.controller;

import com.ogbuilds.url_shortener_app.url.dto.*;
import com.ogbuilds.url_shortener_app.url.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    ) {
        return urlService.createShortUrl(request);
    }

    @GetMapping("/{shortCode}")
    public void redirect(
            @PathVariable("shortCode") String shortCode,
            HttpServletResponse response
    ) throws IOException {
        response.sendRedirect(urlService.getOriginalUrl(shortCode));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Long id) {

        urlService.deleteUrl(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    public UrlResponse getUrl(@PathVariable Long id) {

        return urlService.getUrl(id);
    }

    @PutMapping("/id/{id}")
    public UrlResponse updateUrl(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUrlRequest request) {

        return urlService.updateUrl(id, request);
    }

    @GetMapping("/id/{id}/analytics")
    public UrlAnalyticsResponse getAnalytics(
            @PathVariable Long id) {

        return urlService.getAnalytics(id);
    }

}
