package com.ogbuilds.url_shortener_app.kafka.consumer;

import com.ogbuilds.url_shortener_app.kafka.event.UrlClickEvent;
import com.ogbuilds.url_shortener_app.url.exception.ShortUrlNotFoundException;
import com.ogbuilds.url_shortener_app.url.repository.UrlRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlClickConsumer {

    private final UrlRepository urlRepository;

    @KafkaListener(
            topics = "url-click-events",
            groupId = "url-shortener-group"
    )
    public void consume(UrlClickEvent event) {

        log.info("Consuming click event: {}", event);

        int updated = urlRepository.incrementClickCount(event.getUrlId());

        if (updated == 0) {
            // No exception swallowing here on purpose: this record either
            // doesn't exist or was deleted after the click was published.
            // Let it surface so the error handler / DLT can decide what to do.
            throw new ShortUrlNotFoundException(
                    "URL with id " + event.getUrlId() + " not found while processing click event"
            );
        }

        log.info("Click count updated for urlId={}", event.getUrlId());
    }

    @PostConstruct
    public void init() {
        log.info("UrlClickConsumer bean created");
    }

}