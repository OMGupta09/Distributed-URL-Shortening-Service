package com.ogbuilds.url_shortener_app.url.entity;

import com.ogbuilds.url_shortener_app.common.entity.BaseEntity;
import com.ogbuilds.url_shortener_app.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Url extends BaseEntity {

    @Column(nullable = false,columnDefinition = "TEXT")
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 10)
    private String shortCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User owner;

    @Column
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Long clickCount = 0L;

    private LocalDateTime lastAccessedAt;

}
