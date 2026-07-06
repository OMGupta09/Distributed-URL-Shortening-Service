package com.ogbuilds.url_shortener_app.url.repository;

import com.ogbuilds.url_shortener_app.url.entity.Url;
import com.ogbuilds.url_shortener_app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    List<Url> findAllByOwner(User owner);

}
