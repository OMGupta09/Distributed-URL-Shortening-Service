package com.ogbuilds.url_shortener_app.url.repository;

import com.ogbuilds.url_shortener_app.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Integer> {

    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

}
