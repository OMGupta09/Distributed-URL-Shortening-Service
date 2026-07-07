package com.ogbuilds.url_shortener_app.url.controller;

import com.ogbuilds.url_shortener_app.url.dto.*;
import com.ogbuilds.url_shortener_app.url.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ShortUrlResponse createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request) {

        return urlService.createShortUrl(request);
    }

    // Public redirect endpoint
    @GetMapping("/{shortCode}")
    public void redirect(
            @PathVariable String shortCode,
            HttpServletResponse response
    ) throws IOException {

        response.sendRedirect(urlService.getOriginalUrl(shortCode));
    }

    // Current user's URLs
    @GetMapping("/me")
    public List<UrlResponse> getMyUrls() {

        return urlService.getMyUrls();
    }

    @GetMapping("/id/{urlId}")
    public UrlResponse getUrl(
            @PathVariable Long urlId) {

        return urlService.getUrl(urlId);
    }

    @PutMapping("/id/{urlId}")
    public UrlResponse updateUrl(
            @PathVariable Long urlId,
            @Valid @RequestBody UpdateUrlRequest request) {

        return urlService.updateUrl(urlId, request);
    }

    @DeleteMapping("/id/{urlId}")
    public ResponseEntity<Void> deleteUrl(
            @PathVariable Long urlId) {

        urlService.deleteUrl(urlId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{urlId}/analytics")
    public UrlAnalyticsResponse getAnalytics(
            @PathVariable Long urlId) {

        return urlService.getAnalytics(urlId);
    }

    @GetMapping(
            value = "/id/{urlId}/qr",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] generateQrCode(
            @PathVariable Long urlId) {

        return urlService.generateQrCode(urlId);
    }
}