package com.ogbuilds.url_shortener_app.url.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogbuilds.url_shortener_app.kafka.event.UrlClickEvent;
import com.ogbuilds.url_shortener_app.kafka.producer.UrlClickProducer;
import com.ogbuilds.url_shortener_app.url.dto.*;
import com.ogbuilds.url_shortener_app.url.entity.Url;
import com.ogbuilds.url_shortener_app.url.exception.AliasAlreadyExistsException;
import com.ogbuilds.url_shortener_app.url.exception.ShortUrlNotFoundException;
import com.ogbuilds.url_shortener_app.url.exception.UnauthorizedUrlAccessException;
import com.ogbuilds.url_shortener_app.url.exception.UrlExpiredException;
import com.ogbuilds.url_shortener_app.url.mapper.UrlMapper;
import com.ogbuilds.url_shortener_app.url.repository.UrlRepository;
import com.ogbuilds.url_shortener_app.url.util.QrCodeGenerator;
import com.ogbuilds.url_shortener_app.url.util.ShortCodeGenerator;
import com.ogbuilds.url_shortener_app.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

    @Value("${app.base-url}")
    private String baseUrl;

    private final RedisTemplate<String, String> redisTemplate;
    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;
    private final UrlClickProducer urlClickProducer;
    private final ObjectMapper objectMapper;

    @Override
    public ShortUrlResponse createShortUrl(CreateShortUrlRequest request) {

        Url url = urlMapper.toEntity(request);

        String shortCode;

        if (request.getCustomAlias() != null &&
                !request.getCustomAlias().isBlank()) {

            if (urlRepository.existsByShortCode(request.getCustomAlias())) {
                throw new AliasAlreadyExistsException(
                        "Custom alias already exists."
                );
            }

            shortCode = request.getCustomAlias();

        } else {

            do {
                shortCode = ShortCodeGenerator.generate();
            } while (urlRepository.existsByShortCode(shortCode));
        }

        url.setShortCode(shortCode);
        url.setOwner(getCurrentUser());

        urlRepository.save(url);

        return new ShortUrlResponse(
                url.getOriginalUrl(),
                url.getShortCode(),
                baseUrl.replaceAll("/+$", "")
                        + "/urls/"
                        + url.getShortCode()
        );
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        /*
         * 1. Check Redis first.
         */
        String cachedData = redisTemplate.opsForValue().get(shortCode);

        if (cachedData != null) {

            try {
                CachedUrl cachedUrl =
                        objectMapper.readValue(
                                cachedData,
                                CachedUrl.class
                        );

                /*
                 * URL was found in cache.
                 * Publish click event with the actual DB URL ID.
                 */
                urlClickProducer.publishClickEvent(
                        new UrlClickEvent(
                                cachedUrl.urlId(),
                                shortCode
                        )
                );

                return cachedUrl.originalUrl();

            } catch (JsonProcessingException ex) {

                /*
                 * Corrupted/invalid cache entry.
                 * Delete it and fall back to MySQL.
                 */
                log.warn(
                        "Invalid Redis cache entry for shortCode={}. Removing cache.",
                        shortCode
                );

                redisTemplate.delete(shortCode);
            }
        }

        /*
         * 2. Cache miss → fetch from MySQL.
         */
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(
                                "Short URL not found"
                        )
                );

        /*
         * 3. Check expiration.
         */
        if (url.getExpiresAt() != null &&
                LocalDateTime.now().isAfter(url.getExpiresAt())) {

            throw new UrlExpiredException(
                    "This URL has expired."
            );
        }

        /*
         * 4. Publish analytics event.
         */
        urlClickProducer.publishClickEvent(
                new UrlClickEvent(
                        url.getId(),
                        shortCode
                )
        );

        /*
         * 5. Calculate Redis TTL.
         */
        Duration ttl = Duration.ofHours(24);

        if (url.getExpiresAt() != null) {

            ttl = Duration.between(
                    LocalDateTime.now(),
                    url.getExpiresAt()
            );
        }

        /*
         * 6. Cache urlId + originalUrl.
         */
        CachedUrl cachedUrl = new CachedUrl(
                url.getId(),
                url.getOriginalUrl()
        );

        try {

            String json =
                    objectMapper.writeValueAsString(cachedUrl);

            redisTemplate.opsForValue().set(
                    shortCode,
                    json,
                    ttl
            );

        } catch (JsonProcessingException ex) {

            log.warn(
                    "Failed to cache URL for shortCode={}",
                    shortCode,
                    ex
            );
        }


        return url.getOriginalUrl();
    }

    @Override
    public List<UrlResponse> getMyUrls() {

        User currentUser = getCurrentUser();

        return urlRepository.findAllByOwner(currentUser)
                .stream()
                .map(urlMapper::toUrlResponse)
                .toList();
    }

    @Override
    public void deleteUrl(Long urlId) {

        Url url = getOwnedUrl(urlId);

        /*
         * Remove cached entry.
         */
        redisTemplate.delete(url.getShortCode());

        urlRepository.delete(url);
    }

    @Override
    public UrlResponse getUrl(Long urlId) {

        return urlMapper.toUrlResponse(
                getOwnedUrl(urlId)
        );
    }

    @Override
    public UrlResponse updateUrl(
            Long urlId,
            UpdateUrlRequest request) {

        Url url = getOwnedUrl(urlId);

        url.setOriginalUrl(request.getOriginalUrl());

        urlRepository.save(url);

        /*
         * Remove stale cached value.
         */
        redisTemplate.delete(url.getShortCode());

        return urlMapper.toUrlResponse(url);
    }

    @Override
    public UrlAnalyticsResponse getAnalytics(Long urlId) {

        Url url = getOwnedUrl(urlId);

        return urlMapper.toAnalyticsResponse(url);
    }

    @Override
    public byte[] generateQrCode(Long urlId) {

        Url url = getOwnedUrl(urlId);

        String shortUrl =
                baseUrl.replaceAll("/+$", "")
                        + "/urls/"
                        + url.getShortCode();

        return QrCodeGenerator.generate(shortUrl);
    }

    private Url getOwnedUrl(Long urlId) {

        User currentUser = getCurrentUser();

        Url url = urlRepository.findById(urlId)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(
                                "URL not found"
                        )
                );

        if (currentUser.getId() != url.getOwner().getId()) {
            throw new UnauthorizedUrlAccessException(
                    "You are not authorized to access this URL."
            );
        }

        return url;
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (User) authentication.getPrincipal();
    }

    private record CachedUrl(
            Long urlId,
            String originalUrl
    ) {
    }
}