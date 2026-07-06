package com.ogbuilds.url_shortener_app.url.service;

import com.ogbuilds.url_shortener_app.url.dto.*;
import com.ogbuilds.url_shortener_app.url.entity.Url;
import com.ogbuilds.url_shortener_app.url.exception.AliasAlreadyExistsException;
import com.ogbuilds.url_shortener_app.url.exception.ShortUrlNotFoundException;
import com.ogbuilds.url_shortener_app.url.exception.UnauthorizedUrlAccessException;
import com.ogbuilds.url_shortener_app.url.exception.UrlExpiredException;
import com.ogbuilds.url_shortener_app.url.mapper.UrlMapper;
import com.ogbuilds.url_shortener_app.url.repository.UrlRepository;
import com.ogbuilds.url_shortener_app.url.util.ShortCodeGenerator;
import com.ogbuilds.url_shortener_app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;

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
                "http://localhost:8080/urls/" + url.getShortCode());

    }

    @Override
    public String getOriginalUrl(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException("Short URL not found"));

        if (url.getExpiresAt() != null &&
                LocalDateTime.now().isAfter(url.getExpiresAt())) {
            throw new UrlExpiredException("This URL has expired.");
        }

        url.setClickCount(url.getClickCount() + 1);

        url.setLastAccessedAt(LocalDateTime.now());

        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    @Override
    public List<UrlResponse> getMyUrls() {

        User currentUser = getCurrentUser();

        List<Url> urls = urlRepository.findAllByOwner(currentUser);

        return urls.stream()
                .map(urlMapper::toUrlResponse)
                .toList();
    }

    @Override
    public void deleteUrl(Long id) {

        Url url = getOwnedUrl(id);

        urlRepository.delete(url);
    }

    @Override
    public UrlResponse getUrl(Long id) {

        return urlMapper.toUrlResponse(getOwnedUrl(id));
    }

    @Override
    public UrlResponse updateUrl(Long id,
                                 UpdateUrlRequest request) {

        Url url = getOwnedUrl(id);

        url.setOriginalUrl(request.getOriginalUrl());

        urlRepository.save(url);

        return urlMapper.toUrlResponse(url);
    }

    private Url getOwnedUrl(Long id) {

        User currentUser = getCurrentUser();

        Url url = urlRepository.findById(id)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException("URL not found"));

        if (url.getOwner().getId() != currentUser.getId()) {
            throw new UnauthorizedUrlAccessException(
                    "You are not authorized to access this URL."
            );
        }

        return url;
    }

    @Override
    public UrlAnalyticsResponse getAnalytics(Long id) {

        Url url = getOwnedUrl(id);

        return urlMapper.toAnalyticsResponse(url);
    }


    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }
}
