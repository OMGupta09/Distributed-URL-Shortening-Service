package com.ogbuilds.url_shortener_app.url.repository;

import com.ogbuilds.url_shortener_app.url.entity.Url;
import com.ogbuilds.url_shortener_app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    List<Url> findAllByOwner(User owner);

    @Modifying
    @Transactional
    @Query("UPDATE Url u SET u.clickCount = u.clickCount + 1, u.lastAccessedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    int incrementClickCount(@Param("id") Long id);

}